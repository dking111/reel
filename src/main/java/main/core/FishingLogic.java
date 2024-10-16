package main.core;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import main.GUI.TextTemp;
import main.GUI.Text;
import main.GameObjects.ChargeMeter;
import main.GameObjects.Fish;
import main.GameObjects.FishingLine;
import main.GameObjects.Player;

public class FishingLogic{


    private FishingLine fishingLine;
    private Fish fish;
    private float fishWaitTimer;

    private Random random;


    public FishingLogic(){
        random = new Random();

    }


    public FishingState update(boolean isMouseHeld ,boolean isLeftHeld, boolean isRightHeld,ChargeMeter chargeMeter,Player player,float chargePower,GameData gameData,List<Fish> possibleFishes,List<Text> texts,FishingState fishingState){
   if (fishingState != FishingState.FALSE) {
        if (fishingState == FishingState.CHARGING) {
            if (isMouseHeld && chargeMeter != null) {
                chargeMeter.increaseCharge();
            }
        }
        if (fishingState == FishingState.CASTING) {
            if(player.getState()!="casting"){
                player.setState("casting");
                player.refreshAnimation();
                player.setAnimationSpeed(10);

            }
            if (player.getIsAnimationComplete()){
            fishingLine = new FishingLine((player.getX() + player.getW() / 2), player.getY() - Math.round(chargePower * 100), 15, 15, (player.getX() + player.getW()/2 ), (player.getY() + (player.getH()-player.getH()/4 )));
            fishWaitTimer = (1-chargePower) + 1;
            chargePower = 0;
            fishingState = FishingState.WAITING;
            player.setState("idle_fishing");
            player.refreshAnimation();
            player.setAnimationSpeed(5);
            }
        }
        if (fishingState == FishingState.WAITING){
            System.out.println(fishWaitTimer);
            fishWaitTimer-=0.01;
            if(fishWaitTimer<=0){
                fishWaitTimer=0;
            if (fish==null){
                while(fish==null){
                for(Fish possibleFish: possibleFishes){
                    if (random.nextInt(0,10)<possibleFish.getRarity()){
                        fish = possibleFish;
                        break;
                    }
                }
                }
                fish.spawn(gameData.getWater(),fishingLine.getFishingLineFloat().getX(), fishingLine.getFishingLineFloat().getY());
            }
        
            if(fish.getHooked()){
                if(fish.getIsAnimationComplete()){
                fishingState = FishingState.REELING;
                System.out.println("reeling jnow");
                fish.setIsVisible(false);
                }
                
            }
            else{
                fish.update(fishingLine.getFishingLineFloat().getX(),fishingLine.getFishingLineFloat().getY());
            }
        }

        }

        if (fishingState == FishingState.REELING) {
            
            fishingLine.update(isLeftHeld, isRightHeld, isMouseHeld);

            if (isLeftHeld && !isRightHeld){
                player.setState("reelingLeft");
            }
            else if (isRightHeld && !isLeftHeld){
                player.setState("reelingRight");
            }
            else{
                player.setState("reeling");
            }
            if (fishingLine.getIsGameOver()) {
                fish = null;
                fishingLine = null;
                fishingState = FishingState.IDLE;

            }
        }
        if (fishingState== FishingState.CAUGHT){
            fishingLine = null;
            if(player.getState()!="catching"){
            player.setState("catching");
            player.refreshAnimation();
            player.setAnimationSpeed(10);
            }
            if(player.getIsAnimationComplete()){
                fishingState = FishingState.IDLE;
                player.refreshAnimation();
                player.setAnimationSpeed(5);

                Database.setFishWeight(fish);
                texts.add(new TextTemp(1920/4,player.getY()-200,100,fish.getName(),Color.white,100));
                texts.add(new TextTemp(1920/3,player.getY()-100,100,String.valueOf(fish.getWeight())+"lb" ,Color.white,100));
                fish=null;

            }
            
            
        }
        if (fishingState == FishingState.IDLE){
            player.setState("idle_back");
        }













    }


    return fishingState;
}


public FishingLine getFishingLine() {
    return fishingLine;
}

public void setFishingLine(FishingLine fishingLine) {
    this.fishingLine = fishingLine;
}

public Fish getFish() {
    return fish;
}
public void setFish(Fish fish) {
    this.fish = fish;
}

}