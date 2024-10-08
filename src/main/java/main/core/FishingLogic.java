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

    private Boolean isFishing;
    private Boolean isCharging;
    private Boolean isCasting;
    private Boolean isWaitingForFish;
    private Boolean isReeling;
    private Boolean isCaught;

    private Random random;


    public FishingLogic(){
        random = new Random();

    }


    public void update(boolean isMouseHeld ,boolean isLeftHeld, boolean isRightHeld,ChargeMeter chargeMeter,Player player,FishingLine fishingLine,float fishWaitTimer,float chargePower,Fish fish,GameData gameData,List<Fish> possibleFishes,List<Text> texts){

   if (isFishing != null) {

        if (isCharging) {
            if (isMouseHeld && chargeMeter != null) {
                chargeMeter.increaseCharge();
            }
        }
        if (isCasting) {
            if(player.getState()!="casting"){
                player.setState("casting");
                player.refreshAnimation();
                player.setAnimationSpeed(10);

            }
            if (player.getIsAnimationComplete()){
            fishingLine = new FishingLine((player.getX() + player.getW() / 2), player.getY() - Math.round(chargePower * 100), 15, 15, (player.getX() + player.getW()/2 ), (player.getY() + (player.getH()-player.getH()/4 )));
            fishWaitTimer = (1-chargePower) + 1;
            chargePower = 0;
            isCasting = false;
            isWaitingForFish = true;
            player.setState("idle_fishing");
            player.refreshAnimation();
            player.setAnimationSpeed(5);
            }
        }
        if (isWaitingForFish){
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
                isWaitingForFish=false;
                isReeling=true;
                fish.setIsVisible(false);
                }
                
            }
            else{
                fish.update(fishingLine.getFishingLineFloat().getX(),fishingLine.getFishingLineFloat().getY());
            }
        }

        }

        if (isReeling) {
            
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
                isReeling = false;

            }
        }
        if (isCaught){
            fishingLine = null;
            if(player.getState()!="catching"){
            player.setState("catching");
            player.refreshAnimation();
            player.setAnimationSpeed(10);
            }
            if(player.getIsAnimationComplete()){
                isCaught=false;
                player.refreshAnimation();
                player.setAnimationSpeed(5);

                Database.setFishWeight(fish);
                texts.add(new TextTemp(1920/4,1080/2,100,fish.getName(),Color.white,100));
                texts.add(new TextTemp(1920/3,1080/2+100,100,String.valueOf(fish.getWeight())+"lb" ,Color.white,100));
                fish=null;

            }
            
            
        }
        if (isFishing && !isCaught && !isReeling && !isCasting && !isCharging && !isWaitingForFish){
            player.setState("idle_back");
        }













    }



}
}