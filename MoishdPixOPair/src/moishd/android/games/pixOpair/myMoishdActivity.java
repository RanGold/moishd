package moishd.android.games.pixOpair;

import java.util.Random;

import moishd.android.R;
import moishd.android.games.GameActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class myMoishdActivity extends GameActivity {
	public boolean nakadeal=false;
	public boolean zeroremoved=false;
	public boolean openbygoldkey=false;
	public boolean hundredsna=false;
	public boolean facedup=false;
	public boolean matchdeck2=false;
	public boolean matchdeck1=false;
	public boolean deck1=false;
	public boolean deck2=false;
	public boolean checkside=false;
	public boolean checkside2=false;
	public boolean lastdeck=false;
	public boolean activc1=false;
	public boolean activc2=false;	
	public boolean activc3=false;
	public boolean activc4=false;
	public boolean activc5=false;
	public boolean activc6=false;
	public boolean activc7=false;
	public boolean activc8=false;
	public boolean activc9=false;	
	public boolean activc10=false;
	public boolean activc11=false;
	public boolean activc12=false;
	public boolean activc13=false;
	public boolean activc14=false;
	public boolean activc15=false;
	public boolean activc16=false;
	public boolean activc17=false;
	public boolean activc18=false;
	public boolean activc19=false;
	public boolean activc20=false;
	public boolean activc21=false;
	public boolean activc22=false;
	public boolean activc23=false;
	public boolean activc24=true;
	public boolean activc25=true;
	public boolean activc26=true;
	public boolean activc27=true;
	public boolean activc28=true;

	public boolean gipit=false;
	public boolean goldkeyOn=false;
	public int gkatch=0,jkatch=0,ikatch=0;
	public int presentCard=28;
	public int counterCard=28;	
	public int allcards=52;
	public int removedCard=0;
	public int scores=0;
	public String elapsedplay="";
	public long tseconds=0;
	public int ones=0;
	public int tenths=-1;
	public int hundreds=-1;
	public int goldkeybilang=1;
	public int greenkeybilang=1;
	public int bilangclosed=0;
	public int closedbilang=0;
	public boolean[] match2deck1 = new boolean[28];
	public boolean[] match2deck2 = new boolean[28];
	public boolean[] match2deck3 = new boolean[28];
	public boolean[] latag = new boolean[28];
	public boolean[] ocard = new boolean[28];
	public boolean[] clickna  = new boolean[52];
	public boolean[] openbygold = new boolean[28];
	public boolean[] flipone =new boolean[28];
	public int[] matchcards = new int[2];
	public int[] goldvalues = new int[1];
	public int[] slottedDeck = new int[52];

	ImageView card1 ;
	ImageView card2 ;
	ImageView card3 ;
	ImageView card4 ;
	ImageView card5 ;
	ImageView card6 ;
	ImageView card7 ;
	ImageView card8 ;
	ImageView card9 ;
	ImageView card10 ;
	ImageView card11 ;
	ImageView card12 ;
	ImageView card13 ;
	ImageView card14 ;
	ImageView card15 ;
	ImageView card16 ;
	ImageView card17 ;
	ImageView card18 ;
	ImageView card19 ;
	ImageView card20 ;
	ImageView card21 ;
	ImageView card22 ;
	ImageView card23 ;
	ImageView card24 ;
	ImageView card25 ;
	ImageView card26 ;
	ImageView card27 ;
	ImageView card28 ;
	ImageView card29 ;
	ImageView dealDeck1 ;
	ImageView dealDeck2 ;
	ImageView dealDeck3 ;
	ImageView goldkey ;
	ImageView greenkey ;
	ImageView numgoldkey ;
	ImageView numgreenkey ;
	ImageView onesdigit ;
	ImageView tenthsdigit ;
	ImageView hundredsdigit;

	
	LinearLayout scoreArea;
	Chronometer mChronometer;
	
	Animation myObjectRotate1;
	Animation myObjectRotate2;
	Animation myObjectRotate3;
	Animation myObjectRotate4;
	Animation myObjectRotate5;
	Animation myObjectRotate6;
	Animation myObjectRotate7;
	Animation myObjectRotate8;
	Animation myObjectRotate9;
	Animation myObjectRotate10;
	Animation myObjectRotate11;
	Animation myObjectRotate12;
	Animation myObjectRotate13;
	Animation myObjectRotate14;
	Animation myObjectRotate15;
	Animation myObjectRotate16;
	Animation myObjectRotate17;
	Animation myObjectRotate18;
	Animation myObjectRotate19;
	Animation myObjectRotate20;
	Animation myObjectRotate21;
	Animation myObjectRotate22;
	Animation myObjectRotate23;
	Animation myObjectRotate24;
	Animation myObjectRotate25;
	Animation myObjectRotate26;
	Animation myObjectRotate27;
	Animation myObjectRotate28;
	soundlife myshuffle;
	soundlife mymagicw;
	soundlife mycrowd;
	soundlife mypageturn;
	soundlife mysiren;
	
    static final private int ADD_NEW_TODO = Menu.FIRST;
    static final private int REMOVE_TODO = Menu.FIRST + 1;
	public int[] tableCard(int layCard) {
		int[] cardPartner = new int[4];
		int yg=0;
		for (yg=0;yg<4;yg++) {
			cardPartner[yg]=-1;
		}
		switch (layCard) {
		case(0):cardPartner[0]=44;cardPartner[1]=45;cardPartner[2]=46;cardPartner[3]=47;break;
		case(1):cardPartner[0]=44;cardPartner[1]=45;cardPartner[2]=46;cardPartner[3]=47;break;
		case(2):cardPartner[0]=44;cardPartner[1]=45;cardPartner[2]=46;cardPartner[3]=47;break;
		case(3):cardPartner[0]=44;cardPartner[1]=45;cardPartner[2]=46;cardPartner[3]=47;break;
		case(4):cardPartner[0]=40;cardPartner[1]=41;cardPartner[2]=42;cardPartner[3]=43;break;
		case(5):cardPartner[0]=40;cardPartner[1]=41;cardPartner[2]=42;cardPartner[3]=43;break;
		case(6):cardPartner[0]=40;cardPartner[1]=41;cardPartner[2]=42;cardPartner[3]=43;break;
		case(7):cardPartner[0]=40;cardPartner[1]=41;cardPartner[2]=42;cardPartner[3]=43;break;
		case(8):cardPartner[0]=16;cardPartner[1]=17;cardPartner[2]=18;cardPartner[3]=19;break;
		case(9):cardPartner[0]=16;cardPartner[1]=17;cardPartner[2]=18;cardPartner[3]=19;break;
		case(10):cardPartner[0]=16;cardPartner[1]=17;cardPartner[2]=18;cardPartner[3]=19;break;
		case(11):cardPartner[0]=16;cardPartner[1]=17;cardPartner[2]=18;cardPartner[3]=19;break;
		case(12):cardPartner[0]=14;cardPartner[1]=15;break;
		case(13):cardPartner[0]=14;cardPartner[1]=15;break;
		case(14):cardPartner[0]=12;cardPartner[1]=13;break;
		case(15):cardPartner[0]=12;cardPartner[1]=13;break;
		case(16):cardPartner[0]=8;cardPartner[1]=9;cardPartner[2]=10;cardPartner[3]=11;break;
		case(17):cardPartner[0]=8;cardPartner[1]=9;cardPartner[2]=10;cardPartner[3]=11;break;
		case(18):cardPartner[0]=8;cardPartner[1]=9;cardPartner[2]=10;cardPartner[3]=11;break;
		case(19):cardPartner[0]=8;cardPartner[1]=9;cardPartner[2]=10;cardPartner[3]=11;break;
		case(20):cardPartner[0]=36;cardPartner[1]=37;cardPartner[2]=38;cardPartner[3]=39;break;
		case(21):cardPartner[0]=36;cardPartner[1]=37;cardPartner[2]=38;cardPartner[3]=39;break;
		case(22):cardPartner[0]=36;cardPartner[1]=37;cardPartner[2]=38;cardPartner[3]=39;break;
		case(23):cardPartner[0]=36;cardPartner[1]=37;cardPartner[2]=38;cardPartner[3]=39;break;
		case(24):cardPartner[0]=32;cardPartner[1]=33;cardPartner[2]=34;cardPartner[3]=35;break;
		case(25):cardPartner[0]=32;cardPartner[1]=33;cardPartner[2]=34;cardPartner[3]=35;break;
		case(26):cardPartner[0]=32;cardPartner[1]=33;cardPartner[2]=34;cardPartner[3]=35;break;
		case(27):cardPartner[0]=32;cardPartner[1]=33;cardPartner[2]=34;cardPartner[3]=35;break;
		case(28):cardPartner[0]=48;cardPartner[1]=49;cardPartner[2]=50;cardPartner[3]=51;break;
		case(29):cardPartner[0]=48;cardPartner[1]=49;cardPartner[2]=50;cardPartner[3]=51;break;
		case(30):cardPartner[0]=48;cardPartner[1]=49;cardPartner[2]=50;cardPartner[3]=51;break;
		case(31):cardPartner[0]=48;cardPartner[1]=49;cardPartner[2]=50;cardPartner[3]=51;break;
		case(32):cardPartner[0]=24;cardPartner[1]=25;cardPartner[2]=26;cardPartner[3]=27;break;
		case(33):cardPartner[0]=24;cardPartner[1]=25;cardPartner[2]=26;cardPartner[3]=27;break;
		case(34):cardPartner[0]=24;cardPartner[1]=25;cardPartner[2]=26;cardPartner[3]=27;break;
		case(35):cardPartner[0]=24;cardPartner[1]=25;cardPartner[2]=26;cardPartner[3]=27;break;
		case(36):cardPartner[0]=20;cardPartner[1]=21;cardPartner[2]=22;cardPartner[3]=23;break;
		case(37):cardPartner[0]=20;cardPartner[1]=21;cardPartner[2]=22;cardPartner[3]=23;break;
		case(38):cardPartner[0]=20;cardPartner[1]=21;cardPartner[2]=22;cardPartner[3]=23;break;
		case(39):cardPartner[0]=20;cardPartner[1]=21;cardPartner[2]=22;cardPartner[3]=23;break;
		case(40):cardPartner[0]=4;cardPartner[1]=5;cardPartner[2]=6;cardPartner[3]=7;break;
		case(41):cardPartner[0]=4;cardPartner[1]=5;cardPartner[2]=6;cardPartner[3]=7;break;
		case(42):cardPartner[0]=4;cardPartner[1]=5;cardPartner[2]=6;cardPartner[3]=7;break;
		case(43):cardPartner[0]=4;cardPartner[1]=5;cardPartner[2]=6;cardPartner[3]=7;break;
		case(44):cardPartner[0]=0;cardPartner[1]=1;cardPartner[2]=2;cardPartner[3]=3;break;
		case(45):cardPartner[0]=0;cardPartner[1]=1;cardPartner[2]=2;cardPartner[3]=3;break;
		case(46):cardPartner[0]=0;cardPartner[1]=1;cardPartner[2]=2;cardPartner[3]=3;break;
		case(47):cardPartner[0]=0;cardPartner[1]=1;cardPartner[2]=2;cardPartner[3]=3;break;
		case(48):cardPartner[0]=28;cardPartner[1]=29;cardPartner[2]=30;cardPartner[3]=31;break;
		case(49):cardPartner[0]=28;cardPartner[1]=29;cardPartner[2]=30;cardPartner[3]=31;break;
		case(50):cardPartner[0]=28;cardPartner[1]=29;cardPartner[2]=30;cardPartner[3]=31;break;
		case(51):cardPartner[0]=28;cardPartner[1]=29;cardPartner[2]=30;cardPartner[3]=31;break;

		}
		return cardPartner;
	}
	
	public int[] renewdDeck(int[] jCards) {
		int i=28,j=28,m=28,h=0;
		int[] k = new int[52];
		for (h=0;h<52;h++) {
			k[h]=jCards[h];
		}
		for (m=28;m<52;m++) {
			k[m]=-1;
		}
		for (i=28;i<52;i++) {
			if (jCards[i]==116)	{
				if (i<51) {
					if (jCards[i+1]!=116) { 
						k[j]=jCards[i+1];
					}
				} 
			} else {
				k[j]=jCards[i];
				j++;
			}
		}

		return k;
	}
	
	public void resetCardStatus() {
		int ino;
		for (ino=0;ino<28;ino++){
		latag[ino]=false;
		clickna[ino]=false;
		}
	}
	public void mysoundfx(int musicno) {
		switch (musicno) {
			case(1) : myshuffle.playSound(1);break;
			case(2)	: mycrowd.playSound(1);break;
			case(3) : mypageturn.playSound(1);break;
			case(4) : mymagicw.playSound(1);break;
			case(5) : mysiren.playSound(1);break;
		}
		
	}
	                
	


	public void counterMcard(int oneortwo) {
		switch (oneortwo) {
			case (1) :bilangclosed++; break;
			case (2) :bilangclosed=bilangclosed+2; break;
		}
		if (bilangclosed==30) {
		
			mysoundfx(2);
			mChronometer.stop();
             String isec="";
            long pseconds=0,pminutes=0;
            long truesec=0;
            long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
            pseconds=elapsedMillis/1000;
            tseconds=pseconds;
            pminutes=(pseconds/60)+ ((pseconds-((pseconds/60)*60))/60);
            truesec=((pseconds-((pseconds/60)*60))%60);
            if (truesec<10) {
            	isec="0"; 
            }
            	else 
            		isec="";
            elapsedplay=pminutes+"m:"+isec+truesec+"s";
            Toast.makeText(myMoishdActivity.this, "Elapsed Time: " + pminutes +"m:"+isec+truesec+"s", 
	    	            Toast.LENGTH_LONG).show(); 
            Win();
            //finish();
		}
	}
	
	public int keyNum(int keyindex) {
		int keyvalue=0;
 		switch (keyindex) {
			case(0)	:keyvalue=R.drawable.zerokey;break;
			case(1)	:keyvalue=R.drawable.onekey;break;
			case(2)	:keyvalue=R.drawable.twokey;break;
			case(3)	:keyvalue=R.drawable.threekey;break;
			case(4)	:keyvalue=R.drawable.fourkey;break;
			case(5)	:keyvalue=R.drawable.fivekey;break;
			case(6)	:keyvalue=R.drawable.sixkey;break;
			case(7)	:keyvalue=R.drawable.sevenkey;break;
			case(8)	:keyvalue=R.drawable.eightkey;break;
			case(9)	:keyvalue=R.drawable.ninekey;break;
		}
		return keyvalue;
	}

		public int valueScoreCard(int sindex) {
			int scorevalue=0;
	 		switch (sindex) {
				case(0)	:scorevalue=R.drawable.scorezero;break;
				case(1)	:scorevalue=R.drawable.scoreone;break;
				case(2)	:scorevalue=R.drawable.scoretwo;break;
				case(3)	:scorevalue=R.drawable.scorethree;break;
				case(4)	:scorevalue=R.drawable.scorefour;break;
				case(5)	:scorevalue=R.drawable.scorefive;break;
				case(6)	:scorevalue=R.drawable.scoresix;break;
				case(7)	:scorevalue=R.drawable.scoreseven;break;
				case(8)	:scorevalue=R.drawable.scoreeight;break;
				case(9)	:scorevalue=R.drawable.scorenine;break;
			}
			return scorevalue;
		}
		public int valueCard(int cndex) {
		int cardIdno=-1;
		switch (cndex) {
		case(0)	:cardIdno=R.drawable.ace1c;break;
		case(1)	:cardIdno=R.drawable.ace1c;break;
		case(2)	:cardIdno=R.drawable.ace1c;break;
		case(3)	:cardIdno=R.drawable.ace1c;break;
		case(4)	:cardIdno=R.drawable.two1c;break;
		case(5)	:cardIdno=R.drawable.two1c;break;
		case(6)	:cardIdno=R.drawable.two1c;break;
		case(7)	:cardIdno=R.drawable.two1c;break;
		case(8)	:cardIdno=R.drawable.three1c;break;
		case(9)	:cardIdno=R.drawable.three1c;break;
		case(10):cardIdno=R.drawable.three1c;break;
		case(11):cardIdno=R.drawable.three1c;break;
		case(12):cardIdno=R.drawable.four1ca;break;
		case(13):cardIdno=R.drawable.four1cb;break;
		case(14):cardIdno=R.drawable.four2ca;break;
		case(15):cardIdno=R.drawable.four2cb;break;
		case(16):cardIdno=R.drawable.five1c;break;
		case(17):cardIdno=R.drawable.five1c;break;
		case(18):cardIdno=R.drawable.five1c;break;
		case(19):cardIdno=R.drawable.five1c;break;
		case(20):cardIdno=R.drawable.six1c;break;
		case(21):cardIdno=R.drawable.six1c;break;
		case(22):cardIdno=R.drawable.six1c;break;
		case(23):cardIdno=R.drawable.six1c;break;
		case(24):cardIdno=R.drawable.seven1c;break;
		case(25):cardIdno=R.drawable.seven1c;break;
		case(26):cardIdno=R.drawable.seven1c;break;
		case(27):cardIdno=R.drawable.seven1c;break;
		case(28):cardIdno=R.drawable.eight1c;break;
		case(29):cardIdno=R.drawable.eight1c;break;
		case(30):cardIdno=R.drawable.eight1c;break;
		case(31):cardIdno=R.drawable.eight1c;break;
		case(32):cardIdno=R.drawable.nine1c;break;		
		case(33):cardIdno=R.drawable.nine1c;break;
		case(34):cardIdno=R.drawable.nine1c;break;
		case(35):cardIdno=R.drawable.nine1c;break;
		case(36):cardIdno=R.drawable.ten1c;break;
		case(37):cardIdno=R.drawable.ten1c;break;
		case(38):cardIdno=R.drawable.ten1c;break;
		case(39):cardIdno=R.drawable.ten1c;break;
		case(40):cardIdno=R.drawable.jack1c;break;
		case(41):cardIdno=R.drawable.jack1c;break;
		case(42):cardIdno=R.drawable.jack1c;break;
		case(43):cardIdno=R.drawable.jack1c;break;
		case(44):cardIdno=R.drawable.queen1c;break;
		case(45):cardIdno=R.drawable.queen1c;break;
		case(46):cardIdno=R.drawable.queen1c;break;
		case(47):cardIdno=R.drawable.queen1c;break;
		case(48):cardIdno=R.drawable.king1c;break;
		case(49):cardIdno=R.drawable.king1c;break;
		case(50):cardIdno=R.drawable.king1c;break;
		case(51):cardIdno=R.drawable.king1c;break;
		case(52):cardIdno=R.drawable.ace11c;break;
		case(53):cardIdno=R.drawable.ace11c;break;
		case(54):cardIdno=R.drawable.ace11c;break;
		case(55):cardIdno=R.drawable.ace11c;break;
		case(56):cardIdno=R.drawable.two11c;break;
		case(57):cardIdno=R.drawable.two11c;break;
		case(58):cardIdno=R.drawable.two11c;break;
		case(59):cardIdno=R.drawable.two11c;break;
		case(60):cardIdno=R.drawable.three11c;break;
		case(61):cardIdno=R.drawable.three11c;break;
		case(62):cardIdno=R.drawable.three11c;break;
		case(63):cardIdno=R.drawable.three11c;break;
		case(64):cardIdno=R.drawable.four11ca;break;
		case(65):cardIdno=R.drawable.four21ca;break;
		case(66):cardIdno=R.drawable.four211ca;break;
		case(67):cardIdno=R.drawable.four211cb;break;
		case(68):cardIdno=R.drawable.five11c;break;
		case(69):cardIdno=R.drawable.five11c;break;
		case(70):cardIdno=R.drawable.five11c;break;
		case(71):cardIdno=R.drawable.five11c;break;
		case(72):cardIdno=R.drawable.six11c;break;
		case(73):cardIdno=R.drawable.six11c;break;
		case(74):cardIdno=R.drawable.six11c;break;
		case(75):cardIdno=R.drawable.six11c;break;
		case(76):cardIdno=R.drawable.seven11c;break;
		case(77):cardIdno=R.drawable.seven11c;break;
		case(78):cardIdno=R.drawable.seven11c;break;
		case(79):cardIdno=R.drawable.seven11c;break;
		case(80):cardIdno=R.drawable.eight11c;break;
		case(81):cardIdno=R.drawable.eight11c;break;
		case(82):cardIdno=R.drawable.eight11c;break;
		case(83):cardIdno=R.drawable.eight11c;break;
		case(84):cardIdno=R.drawable.nine11c;break;		
		case(85):cardIdno=R.drawable.nine11c;break;
		case(86):cardIdno=R.drawable.nine11c;break;
		case(87):cardIdno=R.drawable.nine11c;break;
		case(88):cardIdno=R.drawable.ten11c;break;
		case(89):cardIdno=R.drawable.ten11c;break;
		case(90):cardIdno=R.drawable.ten11c;break;
		case(91):cardIdno=R.drawable.ten11c;break;
		case(92):cardIdno=R.drawable.jack11c;break;
		case(93):cardIdno=R.drawable.jack11c;break;
		case(94):cardIdno=R.drawable.jack11c;break;
		case(95):cardIdno=R.drawable.jack11c;break;
		case(96):cardIdno=R.drawable.queen11c;break;
		case(97):cardIdno=R.drawable.queen11c;break;
		case(98):cardIdno=R.drawable.queen11c;break;
		case(99):cardIdno=R.drawable.queen11c;break;
		case(100):cardIdno=R.drawable.king11c;break;
		case(101):cardIdno=R.drawable.king11c;break;
		case(102):cardIdno=R.drawable.king11c;break;
		case(103):cardIdno=R.drawable.king11c;break;
		case(104):cardIdno=R.drawable.fivepts;break;
		case(107):cardIdno=R.drawable.tenpts;break;
		case(109):cardIdno=R.drawable.fifteenpts;break;
		case(111):cardIdno=R.drawable.twentypts;break;
		case(114):cardIdno=R.drawable.twofivepts;break;
		case(116):cardIdno=R.drawable.onept;break;
		}
		return cardIdno;
	}
		// Menu Definition
		
        public boolean onCreateOptionsMenu(Menu menu) {
          super.onCreateOptionsMenu(menu);

          // Create and add new menu items.
          MenuItem itemAdd = menu.add(0, ADD_NEW_TODO, Menu.NONE,
                                      R.string.add_new);
          MenuItem itemRem = menu.add(0, REMOVE_TODO, Menu.NONE,
                                      R.string.remove);

          // Assign icons
          itemAdd.setIcon(R.drawable.add_new_item);
          itemRem.setIcon(R.drawable.remove_item);

          // Allocate shortcuts to each of them.
          itemAdd.setShortcut('0', 'a');
          itemRem.setShortcut('1', 'r');

          return true;
        }
		  
		  public boolean onOptionsItemSelected(MenuItem item) {
		    super.onOptionsItemSelected(item);

		    switch (item.getItemId()) {
		      case (REMOVE_TODO): {
		    	  Lose();
		    	  //finish();
		        return true;
		      }
		      case (ADD_NEW_TODO): { 
		    	  Lose();
		    	  //finish();
					Intent intent = new Intent (
				    		 this,
				    		 myMoishdActivity.class
				     ) ; 
					startActivity(intent);
		        return true; 
		      }
		    }

		    return false;
		  }		
	public void closedbygold(int notdeal) {
		int aj,sm;
		aj=0;
		sm=-1;
		for (aj=0;aj<23;aj++) {
			if (openbygold[aj]==true) {
				sm=aj;
				if (slottedDeck[sm]<104) {
					switch (sm) {
					case (0):card1.setImageResource(R.drawable.cover5);activc1=false;break;
					case (1):card2.setImageResource(R.drawable.cover5);activc2=false;break;
					case (2):card3.setImageResource(R.drawable.cover5);activc3=false;break;
					case (3):card4.setImageResource(R.drawable.cover5);activc4=false;break;
					case (4):card5.setImageResource(R.drawable.cover5);activc5=false;break;
					case (5):card6.setImageResource(R.drawable.cover5);activc6=false;break;
					case (6):card7.setImageResource(R.drawable.cover5);activc7=false;break;
					case (7):card8.setImageResource(R.drawable.cover5);activc8=false;break;
					case (8):card9.setImageResource(R.drawable.cover5);activc9=false;break;
					case (9):card10.setImageResource(R.drawable.cover5);activc10=false;break;
					case (10):card11.setImageResource(R.drawable.cover5);activc11=false;break;
					case (11):card12.setImageResource(R.drawable.cover5);activc12=false;break;
					case (12):card13.setImageResource(R.drawable.cover5);activc13=false;break;
					case (13):card14.setImageResource(R.drawable.cover5);activc14=false;break;
					case (14):card15.setImageResource(R.drawable.cover5);activc15=false;break;
					case (15):card16.setImageResource(R.drawable.cover5);activc16=false;break;
					case (16):card17.setImageResource(R.drawable.cover5);activc17=false;break;
					case (17):card18.setImageResource(R.drawable.cover5);activc18=false;break;
					case (18):card19.setImageResource(R.drawable.cover5);activc19=false;break;
					case (19):card20.setImageResource(R.drawable.cover5);activc20=false;break;
					case (20):card21.setImageResource(R.drawable.cover5);activc21=false;break;
					case (21):card22.setImageResource(R.drawable.cover5);activc22=false;break;
					case (22):card23.setImageResource(R.drawable.cover5);activc23=false;break;					
					}
					if (notdeal==1) {
						goldkeybilang++;
					}
					numgoldkey.setImageResource(keyNum(goldkeybilang));
					goldkey.setImageResource(R.drawable.goldkey);
				}
				openbygold[aj]=false;
				aj=23;
			}
		}

	}
	
	public void updateScore(int newScore) {
		if (scores>=10) { 
			ones=valueScoreCard(scores%10);
			tenths=valueScoreCard(scores/10);
			if (zeroremoved==false ) {
				scoreArea.removeView(onesdigit);
				if (scores/10>9) {
					hundreds=valueScoreCard((scores/10)/10);
					tenths=valueScoreCard((scores/10)%10);
					ones=valueScoreCard(((scores-(scores/100)*100)%10));
					if (hundredsna==false) {
						onesdigit.setPadding(0,0,0,0);
						tenthsdigit.setPadding(0,0,0,0);
						hundredsdigit.setPadding(55,0,0,0);	
						scoreArea.addView(hundredsdigit);
						scoreArea.addView(tenthsdigit);
						scoreArea.addView(onesdigit);
						hundredsna=true;
					}
					hundredsdigit.setImageResource(hundreds);
					onesdigit.setImageResource(ones);
					tenthsdigit.setImageResource(tenths);

				} else {

				onesdigit.setImageResource(ones);
				tenthsdigit.setImageResource(tenths);
				tenthsdigit.setPadding(95,0,0,0); onesdigit.setPadding(0,0,0,0);
				scoreArea.addView(tenthsdigit);
				scoreArea.addView(onesdigit);
				}
				zeroremoved=true;
				
			} else {
				if (scores/10>9) {
					hundreds=valueScoreCard((scores/10)/10);
					tenths=valueScoreCard((scores/10)%10);
					if (hundredsna==false) {
						scoreArea.removeView(onesdigit);
						scoreArea.removeView(tenthsdigit);										
						tenthsdigit.setPadding(0,0,0,0);
						hundredsdigit.setPadding(55,0,0,0);	
						scoreArea.addView(hundredsdigit);
						scoreArea.addView(tenthsdigit);
						scoreArea.addView(onesdigit);
						hundredsna=true;
					}
					hundredsdigit.setImageResource(hundreds);
				}
				onesdigit.setImageResource(ones);
				tenthsdigit.setImageResource(tenths);
			}
		} else 
			onesdigit.setImageResource(valueScoreCard(scores));
	}
	
	
	public void refreshDeck(int mycard_no) {
		switch (mycard_no) {
		case (1):card1.setImageResource(valueCard(slottedDeck[0]));break;
		case (2):card2.setImageResource(valueCard(slottedDeck[1]));break;
		case (3):card3.setImageResource(valueCard(slottedDeck[2]));break;
		case (4):card4.setImageResource(valueCard(slottedDeck[3]));break;
		case (5):card5.setImageResource(valueCard(slottedDeck[4]));break;
		case (6):card6.setImageResource(valueCard(slottedDeck[5]));break;
		case (7):card7.setImageResource(valueCard(slottedDeck[6]));break;
		case (8):card8.setImageResource(valueCard(slottedDeck[7]));break;
		case (9):card9.setImageResource(valueCard(slottedDeck[8]));break;
		case (10):card10.setImageResource(valueCard(slottedDeck[9]));break;
		case (11):card11.setImageResource(valueCard(slottedDeck[10]));break;
		case (12):card12.setImageResource(valueCard(slottedDeck[11]));break;
		case (13):card13.setImageResource(valueCard(slottedDeck[12]));break;
		case (14):card14.setImageResource(valueCard(slottedDeck[13]));break;
		case (15):card15.setImageResource(valueCard(slottedDeck[14]));break;
		case (16):card16.setImageResource(valueCard(slottedDeck[15]));break;
		case (17):card17.setImageResource(valueCard(slottedDeck[16]));break;
		case (18):card18.setImageResource(valueCard(slottedDeck[17]));break;
		case (19):card19.setImageResource(valueCard(slottedDeck[18]));break;
		case (20):card20.setImageResource(valueCard(slottedDeck[19]));break;
		case (21):card21.setImageResource(valueCard(slottedDeck[20]));break;
		case (22):card22.setImageResource(valueCard(slottedDeck[21]));break;
		case (23):card23.setImageResource(valueCard(slottedDeck[22]));break;
		case (24):card24.setImageResource(valueCard(slottedDeck[23]));break;
		case (25):card25.setImageResource(valueCard(slottedDeck[24]));break;
		case (26):card26.setImageResource(valueCard(slottedDeck[25]));break;
		case (27):card27.setImageResource(valueCard(slottedDeck[26]));break;
		case (28):card28.setImageResource(valueCard(slottedDeck[27]));break;
		}

	}

	public void flipopen() {
		if (ocard[27]==true) {
			activc23=true;
			if (flipone[22]==false) {
				card23.setImageResource(valueCard(slottedDeck[22]));
				flipone[22]=true;
				mysoundfx(3);
			}
		}
		if (ocard[26]==true) {
			activc22=true;
			if (flipone[21]==false) {
				card22.setImageResource(valueCard(slottedDeck[21]));
				flipone[21]=true;
				mysoundfx(3);
			}
		}
		if (ocard[25]==true) {
			activc21=true;
			if (flipone[20]==false) {
				card21.setImageResource(valueCard(slottedDeck[20]));
				flipone[20]=true;
				mysoundfx(3);
			}
		}
		if (ocard[24]==true) {
			activc20=true;
			if (flipone[19]==false) {
				card20.setImageResource(valueCard(slottedDeck[19]));
				flipone[19]=true;
				mysoundfx(3);
			}
		}
		if (ocard[23]==true)  {
			activc19=true;
			if (flipone[18]==false) {
				card19.setImageResource(valueCard(slottedDeck[18]));
				flipone[18]=true;
				mysoundfx(3);
			}
		}
		
		if (ocard[22]==true)  {
			activc18=true;
			if (flipone[17]==false) {
				card18.setImageResource(valueCard(slottedDeck[17]));
				flipone[17]=true;
				mysoundfx(3);
			}
		}
		if ((ocard[22]==true) && (ocard[21]==true)) {
			activc17=true;
			if (flipone[16]==false) {
				card17.setImageResource(valueCard(slottedDeck[16]));
				flipone[16]=true;
				mysoundfx(3);
			}
		}
		if ((ocard[21]==true) && (ocard[20]==true)) {
			activc16=true;
			if (flipone[15]==false) {
				card16.setImageResource(valueCard(slottedDeck[15]));
				flipone[15]=true;
				mysoundfx(3);
			}
		}
		if ((ocard[20]==true) && (ocard[19]==true)) {
			activc15=true;
			if (flipone[14]==false) {
				card15.setImageResource(valueCard(slottedDeck[14]));
				flipone[14]=true;
				mysoundfx(3);
			}
		}
		if ((ocard[19]==true) && (ocard[18]==true)) {
			activc14=true;
			if (flipone[13]==false) {
				card14.setImageResource(valueCard(slottedDeck[13]));
				flipone[13]=true;
				mysoundfx(3);
			}
		}
		if (ocard[18]==true) {
			activc13=true;
			if (flipone[12]==false) {
				card13.setImageResource(valueCard(slottedDeck[12]));
				flipone[12]=true;
				mysoundfx(3);
			}
		}

		if (ocard[17]==true) {
			activc12=true;
			if (flipone[11]==false) {
				card12.setImageResource(valueCard(slottedDeck[11]));
				flipone[11]=true;
				mysoundfx(3);
			}
		}
		if (ocard[16]==true) {
			activc11=true;
			if (flipone[10]==false) {
				card11.setImageResource(valueCard(slottedDeck[10]));
				flipone[10]=true;
				mysoundfx(3);
			}
		}
		if (ocard[15]==true) {
			activc10=true;
			if (flipone[9]==false) {
				card10.setImageResource(valueCard(slottedDeck[9]));
				flipone[9]=true;
				mysoundfx(3);
			}
		}
		if (ocard[14]==true) {
			activc9=true;
			if (flipone[8]==false) {
				card9.setImageResource(valueCard(slottedDeck[8]));
				flipone[8]=true;
				mysoundfx(3);
			}
		}
		if (ocard[13]==true) {
			activc8=true;
			if (flipone[7]==false) {
				card8.setImageResource(valueCard(slottedDeck[7]));
				flipone[7]=true;
				mysoundfx(3);
			}
		}

		if (ocard[12]==true) {
			activc7=true;
			if (flipone[6]==false) {
				card7.setImageResource(valueCard(slottedDeck[6]));
				flipone[6]=true;
				mysoundfx(3);
			}
		}
		if (ocard[11]==true) {
			activc6=true;
			if (flipone[5]==false) {
				card6.setImageResource(valueCard(slottedDeck[5]));
				flipone[5]=true;
				mysoundfx(3);
			}
		}
		if (ocard[10]==true) {
			activc5=true;
			if (flipone[4]==false) {
				card5.setImageResource(valueCard(slottedDeck[4]));
				flipone[4]=true;
				mysoundfx(3);
			}
		}
		if (ocard[9]==true) {
			activc4=true;
			if (flipone[3]==false) {
				card4.setImageResource(valueCard(slottedDeck[3]));
				flipone[3]=true;
				mysoundfx(3);
			}
		}
		if (ocard[8]==true) {
			activc3=true;
			if (flipone[2]==false) {
				card3.setImageResource(valueCard(slottedDeck[2]));
				flipone[2]=true;
				mysoundfx(3);
			}
		}
		if (ocard[7]==true) {
			activc2=true;
			if (flipone[1]==false) {
				card2.setImageResource(valueCard(slottedDeck[1]));
				flipone[1]=true;
				mysoundfx(3);
			}
		}
		if (ocard[6]==true) {
			activc1=true;
			if (flipone[0]==false) {
				card1.setImageResource(valueCard(slottedDeck[0]));
				flipone[0]=true;
				mysoundfx(3);
			}
		}

	}
	

	public void checkAll(int card_no,int katch, int kcatch,int p,int[] matchbox ,boolean nagmatchna) {
		int coverscore,facescore;
		coverscore=0;
		facescore=0;
		switch (card_no) {
		case (1):coverscore=114;break;
		case (2):coverscore=114;break;
		case (3):coverscore=114;break;
		case (4):coverscore=114;break;
		case (5):coverscore=114;break;
		case (6):coverscore=114;break;
		case (7):coverscore=111;break;
		case (8):coverscore=111;break;
		case (9):coverscore=111;break;
		case (10):coverscore=111;break;
		case (11):coverscore=111;break;
		case (12):coverscore=111;break;
		case (13):coverscore=109;break;
		case (14):coverscore=109;break;
		case (15):coverscore=109;break;
		case (16):coverscore=109;break;
		case (17):coverscore=109;break;
		case (18):coverscore=109;break;
		case (19):coverscore=107;break;
		case (20):coverscore=107;break;
		case (21):coverscore=107;break;
		case (22):coverscore=107;break;
		case (23):coverscore=107;break;
		case (24):coverscore=104;break;
		case (25):coverscore=104;break;
		case (26):coverscore=104;break;
		case (27):coverscore=104;break;
		case (28):coverscore=104;break;
		}

		switch (card_no) {
		case (1):facescore=25;break;
		case (2):facescore=25;break;
		case (3):facescore=25;break;
		case (4):facescore=25;break;
		case (5):facescore=25;break;
		case (6):facescore=25;break;
		case (7):facescore=20;break;
		case (8):facescore=20;break;
		case (9):facescore=20;break;
		case (10):facescore=20;break;
		case (11):facescore=20;break;
		case (12):facescore=20;break;
		case (13):facescore=15;break;
		case (14):facescore=15;break;
		case (15):facescore=15;break;
		case (16):facescore=15;break;
		case (17):facescore=15;break;
		case (18):facescore=15;break;
		case (19):facescore=10;break;
		case (20):facescore=10;break;
		case (21):facescore=10;break;
		case (22):facescore=10;break;
		case (23):facescore=10;break;
		case (24):facescore=5;break;
		case (25):facescore=5;break;
		case (26):facescore=5;break;
		case (27):facescore=5;break;
		case (28):facescore=5;break;
		}

		if ((latag[card_no-1]==true) && (match2deck3[card_no-1]==true)){
			if (match2deck2[card_no-1]==false) {
				slottedDeck[card_no-1]=coverscore;
				ocard[card_no-1]=true;
				if (gipit==true) {
					greenkeybilang--;
					if (greenkeybilang<0) {
						greenkeybilang=0;
					}
					numgreenkey.setImageResource(keyNum(greenkeybilang));
					greenkey.setImageResource(R.drawable.greenkey);
				}	
				slottedDeck[gkatch]=116;
				scores=scores+facescore+1;
				updateScore(scores); removedCard++; counterMcard(1);  closedbygold(0);
				dealDeck3.setImageResource(valueCard(slottedDeck[gkatch]));
				deck2=true;
				refreshDeck(card_no); flipopen();
				resetCardStatus();
				gkatch=0;
				if (deck2==true){
					deck2=false;
					dealDeck2.setOnClickListener(new View.OnClickListener() {
						
						public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
							int next2gkatch=jkatch-1;
							int ht=0;
							goldkeyOn=false;
							for (ht=0;ht<52;ht++) {
								clickna[ht]=false;
							}
							if (slottedDeck[jkatch]==116) {
								if (slottedDeck[next2gkatch]!=116)  {
									int p=0,mkatch=0;
									int[] matchbox = new int[4];
									mkatch=slottedDeck[next2gkatch];
									matchbox=tableCard(mkatch);
									dealDeck2.setImageResource(valueCard(slottedDeck[next2gkatch]+52));
									if (activc22==true) {
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[21]) {
												card22.setImageResource(valueCard(slottedDeck[21]+52));
											    	
											        myObjectRotate22.setFillAfter(true);
											        myObjectRotate22.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card22.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card22.startAnimation(myObjectRotate22); mysoundfx(4);
												
												match2deck2[21]=true;
												match2deck3[21]=true;
												latag[21]=true;
												p=4;
											} else
												card22.setImageResource(valueCard(slottedDeck[21]));
										}
									}
									if (activc23==true) {
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[22]) {
												card23.setImageResource(valueCard(slottedDeck[22]+52));
											    	
											        myObjectRotate23.setFillAfter(true);
											        myObjectRotate23.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card23.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card23.startAnimation(myObjectRotate23); mysoundfx(4);
												
												latag[22]=true;
												match2deck2[22]=true;
												match2deck3[22]=true;
												p=4;
											} else
												card23.setImageResource(valueCard(slottedDeck[22]));
										}
									}	
								for (p=0;p<4;p++)  {
									if (matchbox[p]==slottedDeck[23]) {
										card24.setImageResource(valueCard(slottedDeck[23]+52));
										latag[23]=true;
									    	
									        myObjectRotate24.setFillAfter(true);
									        myObjectRotate24.setAnimationListener(new AnimationListener() {
								        	public void onAnimationEnd(Animation _animation) {
								        		//card24.startAnimation(in);
								        		}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
									        });
							        		card24.startAnimation(myObjectRotate24); mysoundfx(4);
										
										match2deck2[23]=true;
										match2deck3[23]=true;
										p=4;	
									} else
										card24.setImageResource(valueCard(slottedDeck[23]));
								}
								for (p=0;p<4;p++)  {
									if (matchbox[p]==slottedDeck[24]) {
										card25.setImageResource(valueCard(slottedDeck[24]+52));
									    	
									        myObjectRotate25.setFillAfter(true);
									        myObjectRotate25.setAnimationListener(new AnimationListener() {
								        	public void onAnimationEnd(Animation _animation) {
								        		//card25.startAnimation(in);
								        		}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
									        });
							        		card25.startAnimation(myObjectRotate25); mysoundfx(4);
										
										latag[24]=true;
										match2deck2[24]=true;
										match2deck3[24]=true;
										p=4;
									} else
										card25.setImageResource(valueCard(slottedDeck[24]));
								}
								for (p=0;p<4;p++)  {
									if (matchbox[p]==slottedDeck[25]) {
										card26.setImageResource(valueCard(slottedDeck[25]+52));
									    	
									        myObjectRotate26.setFillAfter(true);
									        myObjectRotate26.setAnimationListener(new AnimationListener() {
								        	public void onAnimationEnd(Animation _animation) {
								        		//card26.startAnimation(in);
								        		}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
									        });
							        		card26.startAnimation(myObjectRotate26); mysoundfx(4);
										
										latag[25]=true;
										match2deck2[25]=true;
										match2deck3[25]=true;
										p=4;
									} else
										card26.setImageResource(valueCard(slottedDeck[25]));
								}
								for (p=0;p<4;p++)  {
									if (matchbox[p]==slottedDeck[26]) {
										card27.setImageResource(valueCard(slottedDeck[26]+52));
									    	
									        myObjectRotate27.setFillAfter(true);
									        myObjectRotate27.setAnimationListener(new AnimationListener() {
								        	public void onAnimationEnd(Animation _animation) {
								        		//card27.startAnimation(in);
								        		}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
									        });
							        		card27.startAnimation(myObjectRotate27); mysoundfx(4);
										latag[26]=true;
										
										match2deck2[26]=true;
										match2deck3[26]=true;
										p=4;
									} else
										card27.setImageResource(valueCard(slottedDeck[26]));
								}
								for (p=0;p<4;p++)  {
									if (matchbox[p]==slottedDeck[27]) {
										card28.setImageResource(valueCard(slottedDeck[27]+52));
									    	
									        myObjectRotate28.setFillAfter(true);
									        myObjectRotate28.setAnimationListener(new AnimationListener() {
								        	public void onAnimationEnd(Animation _animation) {
								        		//card28.startAnimation(in);
								        		}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
									        });
							        		card28.startAnimation(myObjectRotate28); mysoundfx(4);
										
										latag[27]=true;
										match2deck2[27]=true;
										match2deck3[27]=true;
										p=4;
									} else
										card28.setImageResource(valueCard(slottedDeck[27]));
								}
								//2nd layer
								if (activc16==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[15]) {
											card16.setImageResource(valueCard(slottedDeck[15]+52));
							        			myObjectRotate16.setFillAfter(true);
							        			myObjectRotate16.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card16.startAnimation(myObjectRotate16); mysoundfx(4);												
											
											latag[15]=true;
											match2deck2[15]=true;
											match2deck3[15]=true;
											p=4;
										} else
											card16.setImageResource(valueCard(slottedDeck[15]));
									}
								}
								if (activc17==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[16]) {
											card17.setImageResource(valueCard(slottedDeck[16]+52));
							        			myObjectRotate17.setFillAfter(true);
							        			myObjectRotate17.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card17.startAnimation(myObjectRotate17); mysoundfx(4);												
											latag[16]=true;
											
											match2deck2[16]=true;
											match2deck3[16]=true;
											p=4;
										} else
											card17.setImageResource(valueCard(slottedDeck[16]));
									}
								}
								if (activc18==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[17]) {
											card18.setImageResource(valueCard(slottedDeck[17]+52));
							        			myObjectRotate18.setFillAfter(true);
							        			myObjectRotate18.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card18.startAnimation(myObjectRotate18); mysoundfx(4);												
											
											latag[17]=true;
											match2deck2[17]=true;
											match2deck3[17]=true;
											p=4;
										} else
											card18.setImageResource(valueCard(slottedDeck[17]));
									}
								}
								if (activc19==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[18]) {
											card19.setImageResource(valueCard(slottedDeck[18]+52));
							        			myObjectRotate19.setFillAfter(true);
							        			myObjectRotate19.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card19.startAnimation(myObjectRotate19); mysoundfx(4);												
											
											latag[18]=true;
											match2deck2[18]=true;
											match2deck3[18]=true;
											p=4;
										} else
											card19.setImageResource(valueCard(slottedDeck[18]));
									}
								}
								if (activc20==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[19]) {
											card20.setImageResource(valueCard(slottedDeck[19]+52));
							        			myObjectRotate20.setFillAfter(true);
							        			myObjectRotate20.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card20.startAnimation(myObjectRotate20); mysoundfx(4);												
											
											latag[19]=true;
											match2deck2[19]=true;
											match2deck3[19]=true;
											p=4;
										} else
											card20.setImageResource(valueCard(slottedDeck[19]));
									}
								}
								if (activc21==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[20]) {
											card21.setImageResource(valueCard(slottedDeck[20]+52));
							        			myObjectRotate21.setFillAfter(true);
							        			myObjectRotate21.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card21.startAnimation(myObjectRotate21); mysoundfx(4);												
											
											latag[20]=true;
											match2deck2[20]=true;
											match2deck3[20]=true;
											p=4;
										} else
											card21.setImageResource(valueCard(slottedDeck[20]));
									}
								}
								//3rd layer
								if (activc11==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[10]) {
											card11.setImageResource(valueCard(slottedDeck[10]+52));
							        			myObjectRotate11.setFillAfter(true);
							        			myObjectRotate11.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card11.startAnimation(myObjectRotate11); mysoundfx(4);												
											
											latag[10]=true;
											match2deck2[10]=true;
											match2deck3[10]=true;
											p=4;
										} else
											card11.setImageResource(valueCard(slottedDeck[10]));
									}
								}

								if (activc12==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[11]) {
											card12.setImageResource(valueCard(slottedDeck[11]+52));
							        			myObjectRotate12.setFillAfter(true);
							        			myObjectRotate12.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card12.startAnimation(myObjectRotate12); mysoundfx(4);												
											
											latag[11]=true;
											match2deck2[11]=true;
											match2deck3[11]=true;
											p=4;
										} else
											card12.setImageResource(valueCard(slottedDeck[11]));
									}
								}

								if (activc13==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[12]) {
											card13.setImageResource(valueCard(slottedDeck[12]+52));
							        			myObjectRotate13.setFillAfter(true);
							        			myObjectRotate13.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card13.startAnimation(myObjectRotate13); mysoundfx(4);												
											
											latag[12]=true;
											match2deck2[12]=true;
											match2deck3[12]=true;
											p=4;
										} else
											card13.setImageResource(valueCard(slottedDeck[12]));
									}
								}
								if (activc14==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[13]) {
											card14.setImageResource(valueCard(slottedDeck[13]+52));
							        			myObjectRotate14.setFillAfter(true);
							        			myObjectRotate14.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card14.startAnimation(myObjectRotate14); mysoundfx(4);												
											
											latag[13]=true;
											match2deck2[13]=true;
											match2deck3[13]=true;
											p=4;
										} else
											card14.setImageResource(valueCard(slottedDeck[13]));
									}
								}
								if (activc15==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[14]) {
											card15.setImageResource(valueCard(slottedDeck[14]+52));
							        			myObjectRotate15.setFillAfter(true);
							        			myObjectRotate15.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card15.startAnimation(myObjectRotate15); mysoundfx(4);												
											
											latag[14]=true;
											match2deck2[14]=true;
											match2deck3[14]=true;
											p=4;
										} else
											card15.setImageResource(valueCard(slottedDeck[14]));
									}
								}
								//4th layer
								if (activc7==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[6]) {
											card7.setImageResource(valueCard(slottedDeck[6]+52));
							        			myObjectRotate7.setFillAfter(true);
							        			myObjectRotate7.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card7.startAnimation(myObjectRotate7); mysoundfx(4);												
											
											latag[6]=true;
											match2deck2[6]=true;
											match2deck3[6]=true;
											p=4;
										} else
											card7.setImageResource(valueCard(slottedDeck[6]));
									}
								}
								
								if (activc8==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[7]) {
											card8.setImageResource(valueCard(slottedDeck[7]+52));
							        			myObjectRotate8.setFillAfter(true);
							        			myObjectRotate8.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card8.startAnimation(myObjectRotate8); mysoundfx(4);												
											
											latag[7]=true;
											match2deck2[7]=true;
											match2deck3[7]=true;
											p=4;
										} else
											card8.setImageResource(valueCard(slottedDeck[7]));
									}
								}
								if (activc9==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[8]) {
											card9.setImageResource(valueCard(slottedDeck[8]+52));
							        			myObjectRotate9.setFillAfter(true);
							        			myObjectRotate9.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card9.startAnimation(myObjectRotate9); mysoundfx(4);												
											
											latag[8]=true;
											match2deck2[8]=true;
											match2deck3[8]=true;
											p=4;
										} else
											card9.setImageResource(valueCard(slottedDeck[8]));
									}
								}

								if (activc10==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[9]) {
											card10.setImageResource(valueCard(slottedDeck[9]+52));
							        			myObjectRotate10.setFillAfter(true);
							        			myObjectRotate10.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card10.startAnimation(myObjectRotate10); mysoundfx(4);												
											
											latag[9]=true;
											match2deck2[9]=true;
											match2deck3[9]=true;
											p=4;
										} else
											card10.setImageResource(valueCard(slottedDeck[9]));
									}
								}
								// 5th layer
								if (activc6==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[5]) {
											card6.setImageResource(valueCard(slottedDeck[5]+52));
							        			myObjectRotate6.setFillAfter(true);
							        			myObjectRotate6.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card6.startAnimation(myObjectRotate6); mysoundfx(4);												
											
											latag[5]=true;
											match2deck2[5]=true;
											match2deck3[5]=true;
											p=4;
										} else
											card6.setImageResource(valueCard(slottedDeck[5]));
									}
								}
								if (activc5==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[4]) {
											card5.setImageResource(valueCard(slottedDeck[4]+52));
							        			myObjectRotate5.setFillAfter(true);
							        			myObjectRotate5.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card5.startAnimation(myObjectRotate5); mysoundfx(4);												
											
											latag[4]=true;
											match2deck2[4]=true;
											match2deck3[4]=true;
											p=4;
										} else
											card5.setImageResource(valueCard(slottedDeck[4]));
									}
								}
								if (activc4==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[3]) {
											card4.setImageResource(valueCard(slottedDeck[3]+52));
							        			myObjectRotate4.setFillAfter(true);
							        			myObjectRotate4.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card4.startAnimation(myObjectRotate4); mysoundfx(4);												
											
											latag[3]=true;
											match2deck2[3]=true;
											match2deck3[3]=true;
											p=4;
										} else
											card4.setImageResource(valueCard(slottedDeck[3]));
									}
								}
								//6th layer
								
								if (activc2==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[1]) {
											card2.setImageResource(valueCard(slottedDeck[1]+52));
							        			myObjectRotate2.setFillAfter(true);
							        			myObjectRotate2.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card2.startAnimation(myObjectRotate2); mysoundfx(4);												
											
											latag[1]=true;
											match2deck2[1]=true;
											match2deck3[1]=true;
											p=4;
										} else
											card2.setImageResource(valueCard(slottedDeck[1]));
									}
								}
								if (activc3==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[2]) {
											card3.setImageResource(valueCard(slottedDeck[2]+52));
							        			myObjectRotate3.setFillAfter(true);
							        			myObjectRotate3.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card3.startAnimation(myObjectRotate3); mysoundfx(4);												
											
											latag[2]=true;
											match2deck2[2]=true;
											match2deck3[2]=true;
											p=4;
										} else
											card3.setImageResource(valueCard(slottedDeck[2]));
									}
								}
								//7th layer
									if (activc1==true)	{
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[0]) {
											card1.setImageResource(valueCard(slottedDeck[0]+52));
							        			myObjectRotate1.setFillAfter(true);
							        			myObjectRotate1.setAnimationListener(new AnimationListener() {
							        			public void onAnimationEnd(Animation _animation) {}
							        				public void onAnimationRepeat(Animation _animation) {}
							   	     				public void onAnimationStart(Animation _animation) {}
									        	});
						        				card1.startAnimation(myObjectRotate1); mysoundfx(4);												
											
											latag[0]=true;
											match2deck2[0]=true;
											match2deck3[0]=true;
											p=4;
										} else
											card1.setImageResource(valueCard(slottedDeck[0]));
									}
								}

								
								}
							} else 
								dealDeck2.setImageResource(valueCard(slottedDeck[next2gkatch]));
						}
					});

				}
			}
		}
		else  {
			if (card_no!=22) {	
				if (activc22==true) {
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[21]) {
							card22.setImageResource(valueCard(slottedDeck[21]+52));
							myObjectRotate22.setFillAfter(true);
							myObjectRotate22.setAnimationListener(new AnimationListener() {
								public void onAnimationEnd(Animation _animation) {}
								public void onAnimationRepeat(Animation _animation) {}
								public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[21]==false) {
								card22.startAnimation(myObjectRotate22); mysoundfx(4);
							}
		
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[21];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[21]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[21]=107;
									scores=scores+facescore+10;
									ocard[card_no-1]=true;
									ocard[21]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 							
									card22.setImageResource(valueCard(slottedDeck[21]));
								}	
							}
							p=4;
							match2deck3[21]=false;
							match2deck2[21]=false;
							match2deck1[21]=false;
						} else if (card_no!=22) {
							card22.setImageResource(valueCard(slottedDeck[21])); }
					}
				}
			
			}
			if (card_no!=23) {	
				if (activc23==true) {
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[22]) {
							card23.setImageResource(valueCard(slottedDeck[22]+52));
							
							myObjectRotate23.setFillAfter(true);
							myObjectRotate23.setAnimationListener(new AnimationListener() {
								public void onAnimationEnd(Animation _animation) {}
								public void onAnimationRepeat(Animation _animation) {}
								public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[22]==false) {
								card23.startAnimation(myObjectRotate23); mysoundfx(4);
							}
		
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[22];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[22]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[22]=107;
									scores=scores+facescore+10;
									ocard[card_no-1]=true;
									ocard[22]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card23.setImageResource(valueCard(slottedDeck[22]));
								}	
							}
							p=4;
							match2deck3[22]=false;
							match2deck2[22]=false;
							match2deck1[22]=false;
						} else if (card_no!=23) {
							card23.setImageResource(valueCard(slottedDeck[22])); }
					}
				}
			}
			if (card_no!=24) {	
				for (p=0;p<4;p++)  {
					if (matchbox[p]==slottedDeck[23]) {
						card24.setImageResource(valueCard(slottedDeck[23]+52));
						myObjectRotate24.setFillAfter(true);
						myObjectRotate24.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[23]==false) {
							card24.startAnimation(myObjectRotate24); mysoundfx(4);
						}
	
						if (matchcards[0]==-1) {
							matchcards[0]=katch;
							matchcards[1]=slottedDeck[23];
						} else if (nagmatchna==false) {
							nagmatchna=true;
							if (clickna[23]==true)  {
								slottedDeck[card_no-1]=coverscore;
								slottedDeck[23]=104;
								scores=scores+facescore+5;
								ocard[card_no-1]=true;
								ocard[23]=true;
								if (openbygoldkey==true) {
									openbygoldkey=false;
									goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
									if (goldkeybilang<0) {
										goldkeybilang=0;
									}
									numgoldkey.setImageResource(keyNum(goldkeybilang));
								}	
								matchcards[0]=-1;
								updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
								refreshDeck(card_no); 
								card24.setImageResource(valueCard(slottedDeck[23]));
							}	
						}
						p=4;
						match2deck3[23]=false;
						match2deck2[23]=false;
						match2deck1[23]=false;
					} else if (card_no!=24) {
						card24.setImageResource(valueCard(slottedDeck[23]));}
				}
			}
			if (card_no!=25) {	
				for (p=0;p<4;p++)  {
					if (matchbox[p]==slottedDeck[24]) {
						card25.setImageResource(valueCard(slottedDeck[24]+52));
						myObjectRotate25.setFillAfter(true);
						myObjectRotate25.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[24]==false) {
							card25.startAnimation(myObjectRotate25); mysoundfx(4);
						}
	
							
						if (matchcards[0]==-1) {
							matchcards[0]=katch;
							matchcards[1]=slottedDeck[24];
						} else if (nagmatchna==false) {
							nagmatchna=true;
							if (clickna[24]==true)  {
								slottedDeck[card_no-1]=coverscore;
								slottedDeck[24]=104;
								scores=scores+facescore+5;
								ocard[card_no-1]=true;
								ocard[24]=true;
								if (openbygoldkey==true) {
									openbygoldkey=false;
									goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
									if (goldkeybilang<0) {
										goldkeybilang=0;
									}
									numgoldkey.setImageResource(keyNum(goldkeybilang));
								}	
								matchcards[0]=-1;
								updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
								refreshDeck(card_no); 
								card25.setImageResource(valueCard(slottedDeck[24]));
							}	
						}
						p=4;
						match2deck3[24]=false;
						match2deck2[24]=false;
						match2deck1[24]=false;
					} else if (card_no!=25) {
						card25.setImageResource(valueCard(slottedDeck[24])); }
				}
			}
			if (card_no!=26) {	
				for (p=0;p<4;p++)  {
					if (matchbox[p]==slottedDeck[25]) {
						card26.setImageResource(valueCard(slottedDeck[25]+52));
						myObjectRotate26.setFillAfter(true);
						myObjectRotate26.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[25]==false) {
							card26.startAnimation(myObjectRotate26); mysoundfx(4);
						}
	
						if (matchcards[0]==-1) {
							matchcards[0]=katch;
							matchcards[1]=slottedDeck[25];
						} else if (nagmatchna==false) {
							nagmatchna=true;
							if (clickna[25]==true)  {
								slottedDeck[card_no-1]=coverscore;
								slottedDeck[25]=104;
								scores=scores+facescore+5;
								ocard[card_no-1]=true;
								ocard[25]=true;
								if (openbygoldkey==true) {
									openbygoldkey=false;
									goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
									if (goldkeybilang<0) {
										goldkeybilang=0;
									}
									numgoldkey.setImageResource(keyNum(goldkeybilang));
								}	
								matchcards[0]=-1;
								updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
								refreshDeck(card_no); 
								card26.setImageResource(valueCard(slottedDeck[25]));
							}	
						}
						p=4;
						match2deck3[25]=false;
						match2deck2[25]=false;
						match2deck1[25]=false;
					} else if (card_no!=26) {
						card26.setImageResource(valueCard(slottedDeck[25])); }
				}
			}
			if (card_no!=27) {	
				for (p=0;p<4;p++)  {
					if (matchbox[p]==slottedDeck[26]) {
						card27.setImageResource(valueCard(slottedDeck[26]+52));
						myObjectRotate27.setFillAfter(true);
						myObjectRotate27.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[26]==false) {
							card27.startAnimation(myObjectRotate27); mysoundfx(4);
						}
	
						if (matchcards[0]==-1) {
							matchcards[0]=katch;
							matchcards[1]=slottedDeck[26];
						} else if (nagmatchna==false) {
							nagmatchna=true;
							if (clickna[26]==true) { 
								slottedDeck[card_no-1]=coverscore;
								slottedDeck[26]=104;
								scores=scores+facescore+5;
								ocard[card_no-1]=true;
								ocard[26]=true;
								if (openbygoldkey==true) {
									openbygoldkey=false;
									goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
									if (goldkeybilang<0) {
										goldkeybilang=0;
									}
									numgoldkey.setImageResource(keyNum(goldkeybilang));
								}	
								matchcards[0]=-1;
								updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
								refreshDeck(card_no); 
								card27.setImageResource(valueCard(slottedDeck[26]));
							}	
						}
						p=4;
						match2deck3[26]=false;
						match2deck2[26]=false;
						match2deck1[26]=false;
					} else if (card_no!=27) {
						card27.setImageResource(valueCard(slottedDeck[26])); }
				}
			}
			if (card_no!=28) {	
				for (p=0;p<4;p++)  {
					if (matchbox[p]==slottedDeck[27]) {
						card28.setImageResource(valueCard(slottedDeck[27]+52));
						myObjectRotate28.setFillAfter(true);
						myObjectRotate28.setAnimationListener(new AnimationListener() {
						public void onAnimationEnd(Animation _animation) {
							//card.startAnimation(in);
						}
						public void onAnimationRepeat(Animation _animation) {}
						public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[27]==false) {
							card28.startAnimation(myObjectRotate28); mysoundfx(4);
						}
						if (matchcards[0]==-1) {
							matchcards[0]=katch;
							matchcards[1]=slottedDeck[27];
						} else if (nagmatchna==false) {
							nagmatchna=true;
							if (clickna[27]==true)  {
								slottedDeck[card_no-1]=coverscore;
								slottedDeck[27]=104;
								scores=scores+facescore+5;
								ocard[card_no-1]=true;
								ocard[27]=true;
								if (openbygoldkey==true) {
									openbygoldkey=false;
									goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
									if (goldkeybilang<0) {
										goldkeybilang=0;
									}
									numgoldkey.setImageResource(keyNum(goldkeybilang));
									goldkey.setImageResource(R.drawable.goldkey);
								}	
								matchcards[0]=-1;
								updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
								refreshDeck(card_no); 
								card28.setImageResource(valueCard(slottedDeck[27]));
							}
						}
						p=4;
						match2deck3[27]=false;
						match2deck2[27]=false;
						match2deck1[27]=false;
					} else if (card_no!=28) {
						card28.setImageResource(valueCard(slottedDeck[27]));}
				}
			}
			//2nd layer
			if (card_no!=16) {	
				if (activc16==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[15]) {
							card16.setImageResource(valueCard(slottedDeck[15]+52));
							myObjectRotate16.setFillAfter(true);
							myObjectRotate16.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[15]==false) {
								card16.startAnimation(myObjectRotate16); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[15];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[15]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[15]=109;
									scores=scores+facescore+15;
									ocard[card_no-1]=true;
									ocard[15]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card16.setImageResource(valueCard(slottedDeck[15]));
								}
							}
							p=4;
							match2deck3[15]=false;
							match2deck2[15]=false;
							match2deck1[15]=false;
						} else if ((card_no!=16) &&  (activc16==true)) {
							card16.setImageResource(valueCard(slottedDeck[15])); }
					}
				}
			}
			if (card_no!=17) {	
				if (activc17==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[16]) {
							card17.setImageResource(valueCard(slottedDeck[16]+52));
							myObjectRotate17.setFillAfter(true);
							myObjectRotate17.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[16]==false) {
								card17.startAnimation(myObjectRotate17); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[16];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[16]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[16]=109;
									scores=scores+facescore+15;
									ocard[card_no-1]=true;
									ocard[16]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card17.setImageResource(valueCard(slottedDeck[16]));
								}
							}
							p=4;
							match2deck3[16]=false;
							match2deck2[16]=false;
							match2deck1[16]=false;
						} else if ((card_no!=17) && (activc17==true)){
							card17.setImageResource(valueCard(slottedDeck[16])); }
					}
				}
			}
			if (card_no!=18) {	
				if (activc18==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[17]) {
							card18.setImageResource(valueCard(slottedDeck[17]+52));
							myObjectRotate18.setFillAfter(true);
							myObjectRotate18.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[17]==false) {
								card18.startAnimation(myObjectRotate18); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[17];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[17]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[17]=109;
									scores=scores+facescore+15;
									ocard[card_no-1]=true;
									ocard[17]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card18.setImageResource(valueCard(slottedDeck[17]));
								}
							}
							p=4;
							match2deck3[17]=false;
							match2deck2[17]=false;
							match2deck1[17]=false;
						} else if ((card_no!=18) && (activc18==true)) {
							card18.setImageResource(valueCard(slottedDeck[17]));  }
					}
				}
			}
			if (card_no!=19) {	
				if (activc19==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[18]) {
							card19.setImageResource(valueCard(slottedDeck[18]+52));
							myObjectRotate19.setFillAfter(true);
							myObjectRotate19.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[18]==false) {
								card19.startAnimation(myObjectRotate19); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[18];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[18]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[18]=107;
									scores=scores+facescore+10;
									ocard[card_no-1]=true;
									ocard[18]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card19.setImageResource(valueCard(slottedDeck[18]));
								}
							}
							p=4;
							match2deck3[18]=false;
							match2deck2[18]=false;
							match2deck1[18]=false;
						} else if ((card_no!=19) && (activc19==true)) {
							card19.setImageResource(valueCard(slottedDeck[18]));  }
					}
				}
			}
			if (card_no!=20) {	
				if (activc20==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[19]) {
							card20.setImageResource(valueCard(slottedDeck[19]+52));
							myObjectRotate20.setFillAfter(true);
							myObjectRotate20.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[19]==false) {
								card20.startAnimation(myObjectRotate20); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[19];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[19]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[19]=107;
									scores=scores+facescore+10;
									ocard[card_no-1]=true;
									ocard[19]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card20.setImageResource(valueCard(slottedDeck[19]));
								}
							}
							p=4;
							match2deck3[19]=false;
							match2deck2[19]=false;
							match2deck1[19]=false;
						} else  if ((card_no!=20) && (activc20==true)) {
							card20.setImageResource(valueCard(slottedDeck[19]));  }
					}
				}
			}
			if (card_no!=21) {	
				if (activc21==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[20]) {
							card21.setImageResource(valueCard(slottedDeck[20]+52));
							myObjectRotate21.setFillAfter(true);
							myObjectRotate21.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[20]==false) {
								card21.startAnimation(myObjectRotate21); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[20];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[20]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[20]=107;
									scores=scores+facescore+10;
									ocard[card_no-1]=true;
									ocard[20]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card21.setImageResource(valueCard(slottedDeck[20]));
								}
							}
							p=4;
							match2deck3[20]=false;
							match2deck2[20]=false;
							match2deck1[20]=false;
						} else if ((card_no!=21) && (activc21==true)) {
							card21.setImageResource(valueCard(slottedDeck[20]));  }
					}
				}
			}
			
			// 3rd layer
			if (card_no!=11) {	
				if (activc11==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[10]) {
							card11.setImageResource(valueCard(slottedDeck[10]+52));
						myObjectRotate11.setFillAfter(true);
						myObjectRotate11.setAnimationListener(new AnimationListener() {
						public void onAnimationEnd(Animation _animation) {
							//card.startAnimation(in);
						}
						public void onAnimationRepeat(Animation _animation) {}
						public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[10]==false) {
							card11.startAnimation(myObjectRotate11); mysoundfx(4);
						}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[10];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[10]==true)  {
									slottedDeck[10]=111;
									slottedDeck[card_no-1]=coverscore;
									scores=scores+facescore+20;
									ocard[10]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card11.setImageResource(valueCard(slottedDeck[10]));
								}
							}
							p=4;
							match2deck1[10]=false;
							match2deck2[10]=false;
							match2deck3[10]=false;
						} else if ((card_no!=11) && (activc11==true)) {
							card11.setImageResource(valueCard(slottedDeck[10])); }
					}
				}
			}
			if (card_no!=12) {	
				if (activc12==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[11]) {
							card12.setImageResource(valueCard(slottedDeck[11]+52));
						myObjectRotate12.setFillAfter(true);
						myObjectRotate12.setAnimationListener(new AnimationListener() {
						public void onAnimationEnd(Animation _animation) {
							//card.startAnimation(in);
						}
						public void onAnimationRepeat(Animation _animation) {}
						public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[11]==false) {
							card12.startAnimation(myObjectRotate12); mysoundfx(4);
						}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[11];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[11]==true)  {
									slottedDeck[11]=111;
									slottedDeck[card_no-1]=coverscore;
									scores=scores+facescore+20;
									ocard[11]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card12.setImageResource(valueCard(slottedDeck[11]));
								}
							}
							p=4;
							match2deck1[11]=false;
							match2deck2[11]=false;
							match2deck3[11]=false;
						} else  if ((card_no!=12) && (activc12==true)) {
							card12.setImageResource(valueCard(slottedDeck[11]));  }
					}
				}
			}
			if (card_no!=13) {	
				if (activc13==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[12]) {
							card13.setImageResource(valueCard(slottedDeck[12]+52));
						myObjectRotate13.setFillAfter(true);
						myObjectRotate13.setAnimationListener(new AnimationListener() {
						public void onAnimationEnd(Animation _animation) {
							//card.startAnimation(in);
						}
						public void onAnimationRepeat(Animation _animation) {}
						public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[12]==false) {
							card13.startAnimation(myObjectRotate13); mysoundfx(4);
						}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[12];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[12]==true)  {
									slottedDeck[12]=109;
									slottedDeck[card_no-1]=coverscore;
									scores=scores+facescore+15;
									ocard[12]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									card13.setImageResource(valueCard(slottedDeck[12]));
									refreshDeck(card_no); 
								}
							}
							p=4;
							match2deck1[12]=false;
							match2deck2[12]=false;
							match2deck3[12]=false;
						} else if ((card_no!=13) && (activc13==true)) {
							card13.setImageResource(valueCard(slottedDeck[12])); }
					}
				}					
			}	
			if (card_no!=14) {	
				if (activc14==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[13]) {
							card14.setImageResource(valueCard(slottedDeck[13]+52));
						myObjectRotate14.setFillAfter(true);
						myObjectRotate14.setAnimationListener(new AnimationListener() {
						public void onAnimationEnd(Animation _animation) {
							//card.startAnimation(in);
						}
						public void onAnimationRepeat(Animation _animation) {}
						public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[13]==false) {
							card14.startAnimation(myObjectRotate14); mysoundfx(4);
						}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[13];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[13]==true)  {
									slottedDeck[13]=109;
									slottedDeck[card_no-1]=coverscore;
									scores=scores+facescore+15;
									ocard[card_no-1]=true;
									ocard[13]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									card14.setImageResource(valueCard(slottedDeck[13]));
									refreshDeck(card_no); 
								}
							}
							p=4;
							match2deck1[13]=false;
							match2deck2[13]=false;
							match2deck3[13]=false;
						} else if ((card_no!=14) && (activc14==true)) {
							card14.setImageResource(valueCard(slottedDeck[13]));  }
					}
				}					
			}	
			if (card_no!=15) {	
				if (activc15==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[14]) {
							card15.setImageResource(valueCard(slottedDeck[14]+52));
						myObjectRotate15.setFillAfter(true);
						myObjectRotate15.setAnimationListener(new AnimationListener() {
						public void onAnimationEnd(Animation _animation) {
							//card.startAnimation(in);
						}
						public void onAnimationRepeat(Animation _animation) {}
						public void onAnimationStart(Animation _animation) {}
						});
						if (clickna[14]==false) {
							card15.startAnimation(myObjectRotate15); mysoundfx(4);
						}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[14];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[14]==true)  {
									slottedDeck[14]=109;
									slottedDeck[card_no-1]=coverscore;
									scores=scores+facescore+15;
									ocard[card_no-1]=true;
									ocard[14]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									card15.setImageResource(valueCard(slottedDeck[14]));
									refreshDeck(card_no); 
								}
							}
							p=4;
							match2deck1[14]=false;
							match2deck2[14]=false;
							match2deck3[14]=false;
						} else  if ((card_no!=15) && (activc15==true)) {
							card15.setImageResource(valueCard(slottedDeck[14])); }
					}
				}
			}	
			
			//4th layer	
			if (card_no!=7) {	
				if (activc7==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[6]) {
							card7.setImageResource(valueCard(slottedDeck[6]+52));
							myObjectRotate7.setFillAfter(true);
							myObjectRotate7.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[6]==false) {
								card7.startAnimation(myObjectRotate7); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[6];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[6]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[6]=111;
									scores=scores+facescore+20;
									ocard[6]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card7.setImageResource(valueCard(slottedDeck[6]));
								}
							}
							p=4;
							match2deck1[6]=false;
							match2deck2[6]=false;
							match2deck3[6]=false;
						} else  if ((card_no!=7) && (activc7==true)) {
							card7.setImageResource(valueCard(slottedDeck[6]));  }
					}
				}					
			}	
			if (card_no!=8) {	
				if (activc8==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[7]) {
							card8.setImageResource(valueCard(slottedDeck[7]+52));
							myObjectRotate8.setFillAfter(true);
							myObjectRotate8.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[7]==false) {
								card8.startAnimation(myObjectRotate8); mysoundfx(4);
							}
							myObjectRotate8.setFillAfter(true);
							myObjectRotate8.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[7]==false) {
								card8.startAnimation(myObjectRotate8); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[7];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[7]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[7]=111;
									scores=scores+facescore+20;
									ocard[7]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card8.setImageResource(valueCard(slottedDeck[7]));
								}
							}
							p=4;
							match2deck1[7]=false;
							match2deck2[7]=false;
							match2deck3[7]=false;
						} else  if ((card_no!=8) && (activc8==true)) {
							card8.setImageResource(valueCard(slottedDeck[7])); }
					}
				}					
			}	
			if (card_no!=9) {	
				if (activc9==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[8]) {
							card9.setImageResource(valueCard(slottedDeck[8]+52));
							myObjectRotate9.setFillAfter(true);
							myObjectRotate9.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[8]==false) {
								card9.startAnimation(myObjectRotate9); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[8];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[8]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[8]=111;
									scores=scores+facescore+20;
									ocard[8]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card9.setImageResource(valueCard(slottedDeck[8]));
								}
							}
							p=4;
							match2deck1[8]=false;
							match2deck2[8]=false;
							match2deck3[8]=false;
						} else if ((card_no!=9) && (activc9==true)) {
							card9.setImageResource(valueCard(slottedDeck[8]));  }
					}
				}
			}	
			if (card_no!=10) {	
				if (activc10==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[9]) {
							card10.setImageResource(valueCard(slottedDeck[9]+52));
							myObjectRotate10.setFillAfter(true);
							myObjectRotate10.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[9]==false) {
								card10.startAnimation(myObjectRotate10); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[9];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[9]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[9]=111;
									scores=scores+facescore+20;
									ocard[9]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card10.setImageResource(valueCard(slottedDeck[9]));
								}
							}
							p=4;
							match2deck1[9]=false;
							match2deck2[9]=false;
							match2deck3[9]=false;
						} else if ((card_no!=10) && (activc10==true)) {
							card10.setImageResource(valueCard(slottedDeck[9])); }
					}
				}					
			}	
			// 5th layer
			if (card_no!=4) {	
				if (activc4==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[3]) {
							card4.setImageResource(valueCard(slottedDeck[3]+52));
							myObjectRotate4.setFillAfter(true);
							myObjectRotate4.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[3]==false) {
								card4.startAnimation(myObjectRotate4); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[3];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[3]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[3]=114;
									scores=scores+facescore+25;
									ocard[card_no-1]=true;
									ocard[3]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card4.setImageResource(valueCard(slottedDeck[3]));
								}
							}
							p=4;
							match2deck1[3]=false;
							match2deck2[3]=false;
							match2deck3[3]=false;
						} else if ((card_no!=4) && (activc4==true)) {
							card4.setImageResource(valueCard(slottedDeck[3]));  }
					}
				}					
			}	
			if (card_no!=5) {	
					if (activc5==true)	{
						for (p=0;p<4;p++)  {
							if (matchbox[p]==slottedDeck[4]) {
								card5.setImageResource(valueCard(slottedDeck[4]+52));
								myObjectRotate5.setFillAfter(true);
								myObjectRotate5.setAnimationListener(new AnimationListener() {
								public void onAnimationEnd(Animation _animation) {
									//card.startAnimation(in);
								}
								public void onAnimationRepeat(Animation _animation) {}
								public void onAnimationStart(Animation _animation) {}
								});
								if (clickna[4]==false) {
									card5.startAnimation(myObjectRotate5); mysoundfx(4);
								}
								if (matchcards[0]==-1) {
									matchcards[0]=katch;
									matchcards[1]=slottedDeck[4];
								} else if (nagmatchna==false) {
									nagmatchna=true;
									if (clickna[4]==true)  {
										slottedDeck[card_no-1]=coverscore;
										slottedDeck[4]=114;
										scores=scores+facescore+25;
										ocard[4]=true;
										ocard[card_no-1]=true;
										if (openbygoldkey==true) {
											openbygoldkey=false;
											goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
											if (goldkeybilang<0) {
												goldkeybilang=0;
											}
											numgoldkey.setImageResource(keyNum(goldkeybilang));
										}	
										matchcards[0]=-1;
										updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
										refreshDeck(card_no); 
										card5.setImageResource(valueCard(slottedDeck[4]));
									}
								}
								p=4;
								match2deck1[4]=false;
								match2deck2[4]=false;
								match2deck3[4]=false;
							} else  if ((card_no!=5) && (activc5==true)) {
								card5.setImageResource(valueCard(slottedDeck[4])); }
						}
					}					
			}		
			if (card_no!=6) {	
				if (activc6==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[5]) {
							card6.setImageResource(valueCard(slottedDeck[5]+52));
							myObjectRotate6.setFillAfter(true);
							myObjectRotate6.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[5]==false) {
								card6.startAnimation(myObjectRotate6); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[5];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[5]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[5]=114;
									scores=scores+facescore+25;
									ocard[5]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card6.setImageResource(valueCard(slottedDeck[5]));
								}
							}
							p=4;
							match2deck1[5]=false;
							match2deck2[5]=false;
							match2deck3[5]=false;
						} else if ((card_no!=6) && (activc6==true)) {
							card6.setImageResource(valueCard(slottedDeck[5]));  }
					}
				}					
			}	
			// 6th layer
			if (card_no!=2) {	
				if (activc2==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[1]) {
							card2.setImageResource(valueCard(slottedDeck[1]+52));
							myObjectRotate2.setFillAfter(true);
							myObjectRotate2.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[1]==false) {
								card2.startAnimation(myObjectRotate2); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[1];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[1]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[1]=114;
									scores=scores+facescore+25;
									ocard[1]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card2.setImageResource(valueCard(slottedDeck[1]));
								}
							}
							p=4;
							match2deck1[1]=false;
							match2deck2[1]=false;
							match2deck3[1]=false;
						} else  if ((card_no!=2) && (activc2==true)) {
							card2.setImageResource(valueCard(slottedDeck[1]));  }
					}
				}					
			}	
			if (card_no!=3) {	
				if (activc3==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[2]) {
							card3.setImageResource(valueCard(slottedDeck[2]+52));
							myObjectRotate3.setFillAfter(true);
							myObjectRotate3.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[2]==false) {
								card3.startAnimation(myObjectRotate3); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[2];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[2]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[2]=114;
									scores=scores+facescore+25;
									ocard[2]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card3.setImageResource(valueCard(slottedDeck[2]));
								}
							}
							p=4;
							match2deck1[2]=false;
							match2deck2[2]=false;
							match2deck3[2]=false;
						} else  if ((card_no!=3) && (activc3==true)) {
							card3.setImageResource(valueCard(slottedDeck[2]));  }
					}
				}				
			}	
			//7th layer
			if (card_no!=1) {	
				if (activc1==true)	{
					for (p=0;p<4;p++)  {
						if (matchbox[p]==slottedDeck[0]) {
							card1.setImageResource(valueCard(slottedDeck[0]+52));
							myObjectRotate1.setFillAfter(true);
							myObjectRotate1.setAnimationListener(new AnimationListener() {
							public void onAnimationEnd(Animation _animation) {
								//card.startAnimation(in);
							}
							public void onAnimationRepeat(Animation _animation) {}
							public void onAnimationStart(Animation _animation) {}
							});
							if (clickna[0]==false) {
								card1.startAnimation(myObjectRotate1); mysoundfx(4);
							}
							if (matchcards[0]==-1) {
								matchcards[0]=katch;
								matchcards[1]=slottedDeck[0];
							} else if (nagmatchna==false) {
								nagmatchna=true;
								if (clickna[0]==true)  {
									slottedDeck[card_no-1]=coverscore;
									slottedDeck[0]=114;
									scores=scores+facescore+25;
									ocard[0]=true;
									ocard[card_no-1]=true;
									if (openbygoldkey==true) {
										openbygoldkey=false;
										goldkeybilang--;  goldkey.setImageResource(R.drawable.goldkey);
										if (goldkeybilang<0) {
											goldkeybilang=0;
										}
										numgoldkey.setImageResource(keyNum(goldkeybilang));
									}	
									matchcards[0]=-1;
									updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);
									refreshDeck(card_no); 
									card1.setImageResource(valueCard(slottedDeck[0]));
								}
							}
							p=4;
							match2deck1[0]=false;
							match2deck2[0]=false;
							match2deck3[0]=false;
						} else if ((card_no!=1) && (activc1==true)) {
							card1.setImageResource(valueCard(slottedDeck[0]));  }
					}
				}					
			}	
			flipopen();
		}
		
	}
	
	
	public void checkDeck1(int card_no) {
		int coverscore=0,facescore=0;
		
		if ((match2deck1[card_no-1]==true)&&(latag[card_no-1]==true)) {
			resetCardStatus();
			switch (card_no) {
			case (1):coverscore=114;break;
			case (2):coverscore=114;break;
			case (3):coverscore=114;break;
			case (4):coverscore=114;break;
			case (5):coverscore=114;break;
			case (6):coverscore=114;break;
			case (7):coverscore=111;break;
			case (8):coverscore=111;break;
			case (9):coverscore=111;break;
			case (10):coverscore=111;break;
			case (11):coverscore=111;break;
			case (12):coverscore=111;break;
			case (13):coverscore=109;break;
			case (14):coverscore=109;break;
			case (15):coverscore=109;break;
			case (16):coverscore=109;break;
			case (17):coverscore=109;break;
			case (18):coverscore=109;break;
			case (19):coverscore=107;break;
			case (20):coverscore=107;break;
			case (21):coverscore=107;break;
			case (22):coverscore=107;break;
			case (23):coverscore=107;break;
			case (24):coverscore=104;break;
			case (25):coverscore=104;break;
			case (26):coverscore=104;break;
			case (27):coverscore=104;break;
			case (28):coverscore=104;break;
			}

			switch (card_no) {
			case (1):facescore=25;break;
			case (2):facescore=25;break;
			case (3):facescore=25;break;
			case (4):facescore=25;break;
			case (5):facescore=25;break;
			case (6):facescore=25;break;
			case (7):facescore=20;break;
			case (8):facescore=20;break;
			case (9):facescore=20;break;
			case (10):facescore=20;break;
			case (11):facescore=20;break;
			case (12):facescore=20;break;
			case (13):facescore=15;break;
			case (14):facescore=15;break;
			case (15):facescore=15;break;
			case (16):facescore=15;break;
			case (17):facescore=15;break;
			case (18):facescore=15;break;
			case (19):facescore=10;break;
			case (20):facescore=10;break;
			case (21):facescore=10;break;
			case (22):facescore=10;break;
			case (23):facescore=10;break;
			case (24):facescore=5;break;
			case (25):facescore=5;break;
			case (26):facescore=5;break;
			case (27):facescore=5;break;
			case (28):facescore=5;break;
			}

			match2deck1[card_no-1]=false;
			slottedDeck[card_no-1]=coverscore;
			slottedDeck[jkatch-2]=116;
			scores=scores+facescore+1;
			updateScore(scores); removedCard++; counterMcard(1);  closedbygold(0);
			refreshDeck(card_no); 			
			dealDeck1.setImageResource(valueCard(slottedDeck[jkatch-2]));
			ocard[card_no-1]=true;
			if (gipit==true) {
				greenkeybilang--;
				if (greenkeybilang<0) {
					greenkeybilang=0;
				}
				numgreenkey.setImageResource(keyNum(greenkeybilang));
				greenkey.setImageResource(R.drawable.greenkey);
			}	
		}

	}	
	
	public void checkDeck2(int card_no) {
		int coverscore=0,facescore=0;
		if ((match2deck2[card_no-1]==true)&&(latag[card_no-1]==true)) {
			resetCardStatus();
			switch (card_no) {
			case (1):coverscore=114;break;
			case (2):coverscore=114;break;
			case (3):coverscore=114;break;
			case (4):coverscore=114;break;
			case (5):coverscore=114;break;
			case (6):coverscore=114;break;
			case (7):coverscore=111;break;
			case (8):coverscore=111;break;
			case (9):coverscore=111;break;
			case (10):coverscore=111;break;
			case (11):coverscore=111;break;
			case (12):coverscore=111;break;
			case (13):coverscore=109;break;
			case (14):coverscore=109;break;
			case (15):coverscore=109;break;
			case (16):coverscore=109;break;
			case (17):coverscore=109;break;
			case (18):coverscore=109;break;
			case (19):coverscore=107;break;
			case (20):coverscore=107;break;
			case (21):coverscore=107;break;
			case (22):coverscore=107;break;
			case (23):coverscore=107;break;
			case (24):coverscore=104;break;
			case (25):coverscore=104;break;
			case (26):coverscore=104;break;
			case (27):coverscore=104;break;
			case (28):coverscore=104;break;
			}

			switch (card_no) {
			case (1):facescore=25;break;
			case (2):facescore=25;break;
			case (3):facescore=25;break;
			case (4):facescore=25;break;
			case (5):facescore=25;break;
			case (6):facescore=25;break;
			case (7):facescore=20;break;
			case (8):facescore=20;break;
			case (9):facescore=20;break;
			case (10):facescore=20;break;
			case (11):facescore=20;break;
			case (12):facescore=20;break;
			case (13):facescore=15;break;
			case (14):facescore=15;break;
			case (15):facescore=15;break;
			case (16):facescore=15;break;
			case (17):facescore=15;break;
			case (18):facescore=15;break;
			case (19):facescore=10;break;
			case (20):facescore=10;break;
			case (21):facescore=10;break;
			case (22):facescore=10;break;
			case (23):facescore=10;break;
			case (24):facescore=5;break;
			case (25):facescore=5;break;
			case (26):facescore=5;break;
			case (27):facescore=5;break;
			case (28):facescore=5;break;
			}
			match2deck2[card_no-1]=false;
			slottedDeck[card_no-1]=coverscore;
			slottedDeck[jkatch-1]=116;
			scores=scores+facescore+1;
			updateScore(scores); removedCard++; counterMcard(1);  closedbygold(0);
			ocard[card_no-1]=true;
			if (gipit==true) {
				greenkeybilang--;
				if (greenkeybilang<0) {
					greenkeybilang=0;
				}
				numgreenkey.setImageResource(keyNum(greenkeybilang));
				greenkey.setImageResource(R.drawable.greenkey);
			}	
			refreshDeck(card_no); 	
			dealDeck2.setImageResource(valueCard(slottedDeck[jkatch-1]));
			dealDeck1.setOnClickListener(new View.OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int next2jkatch=jkatch-2;
					goldkeyOn=false;
					if (slottedDeck[jkatch-1]==116) {
						if (slottedDeck[next2jkatch]!=116)  {
							int p=0,mkatch=0;
							int[] matchbox = new int[4];
							int ht=0;
							for (ht=0;ht<52;ht++) {
								clickna[ht]=false;
							}
							mkatch=slottedDeck[next2jkatch];
							matchbox=tableCard(mkatch);
							dealDeck1.setImageResource(valueCard(slottedDeck[next2jkatch]+52));
							if (activc22==true)	{
								for (p=0;p<4;p++)  {
									if (matchbox[p]==slottedDeck[21]) {
										card22.setImageResource(valueCard(slottedDeck[21]+52));
										myObjectRotate22.setFillAfter(true);
									        myObjectRotate22.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {}
									        	public void onAnimationRepeat(Animation _animation) {}
									        	public void onAnimationStart(Animation _animation) {}
									        });
								        	card22.startAnimation(myObjectRotate22); mysoundfx(4);
										latag[21]=true;
										match2deck1[21]=true;
										match2deck2[21]=true;
										match2deck3[21]=true;
										p=4;
									} else
										card22.setImageResource(valueCard(slottedDeck[21]));
								}
							}
							if (activc23==true) {
								for (p=0;p<4;p++)  {
									if (matchbox[p]==slottedDeck[22]) {
										card23.setImageResource(valueCard(slottedDeck[22]+52));
										
										myObjectRotate23.setFillAfter(true);
								        	myObjectRotate23.setAnimationListener(new AnimationListener() {
								        		public void onAnimationEnd(Animation _animation) {}
								        		public void onAnimationRepeat(Animation _animation) {}
									        	public void onAnimationStart(Animation _animation) {}
									        });
									        card23.startAnimation(myObjectRotate23); mysoundfx(4);
										latag[22]=true;
										match2deck1[22]=true;
										match2deck2[22]=true;
										match2deck3[22]=true;
										p=4;
									} else
										card23.setImageResource(valueCard(slottedDeck[22]));
								}
							}	
						for (p=0;p<4;p++)  {
							if (matchbox[p]==slottedDeck[23]) {
								card24.setImageResource(valueCard(slottedDeck[23]+52));
							    	
						      		myObjectRotate24.setFillAfter(true);
						        	myObjectRotate24.setAnimationListener(new AnimationListener() {
						        		public void onAnimationEnd(Animation _animation) {}
							        	public void onAnimationRepeat(Animation _animation) {}
							        	public void onAnimationStart(Animation _animation) {}
						        	});
						        	card24.startAnimation(myObjectRotate24); mysoundfx(4);	
																			
								latag[23]=true;
								match2deck1[23]=true;
								match2deck2[23]=true;
								match2deck3[23]=true;
								p=4;	
							} else
								card24.setImageResource(valueCard(slottedDeck[23]));
						}
						for (p=0;p<4;p++)  {
							if (matchbox[p]==slottedDeck[24]) {
								card25.setImageResource(valueCard(slottedDeck[24]+52));
							    	
							        myObjectRotate25.setFillAfter(true);
							        myObjectRotate25.setAnimationListener(new AnimationListener() {
						        	public void onAnimationEnd(Animation _animation) {
							        		//card25.startAnimation(in);
							        	}
							        	public void onAnimationRepeat(Animation _animation) {}
							        	public void onAnimationStart(Animation _animation) {}
							        });
							        card25.startAnimation(myObjectRotate25); mysoundfx(4);
								
								latag[24]=true;
								match2deck1[24]=true;
								match2deck2[24]=true;
								match2deck3[24]=true;
								p=4;
							} else
								card25.setImageResource(valueCard(slottedDeck[24]));
						}
						for (p=0;p<4;p++)  {
							if (matchbox[p]==slottedDeck[25]) {
								card26.setImageResource(valueCard(slottedDeck[25]+52));
							    	
							        myObjectRotate26.setFillAfter(true);
							        myObjectRotate26.setAnimationListener(new AnimationListener() {
						        	public void onAnimationEnd(Animation _animation) {
							        		//card26.startAnimation(in);
							        	}
							        	public void onAnimationRepeat(Animation _animation) {}
							        	public void onAnimationStart(Animation _animation) {}
							        });
							        card26.startAnimation(myObjectRotate26); mysoundfx(4);
								
								latag[25]=true;
								match2deck1[25]=true;
								match2deck2[25]=true;
								match2deck3[25]=true;
								p=4;
							} else
								card26.setImageResource(valueCard(slottedDeck[25]));
						}
						for (p=0;p<4;p++)  {
							if (matchbox[p]==slottedDeck[26]) {
								card27.setImageResource(valueCard(slottedDeck[26]+52));
							    	
							        myObjectRotate27.setFillAfter(true);
							        myObjectRotate27.setAnimationListener(new AnimationListener() {
						        	public void onAnimationEnd(Animation _animation) {
							        		//card27.startAnimation(in);
							        	}
							        	public void onAnimationRepeat(Animation _animation) {}
							        	public void onAnimationStart(Animation _animation) {}
							        });
							        card27.startAnimation(myObjectRotate27); mysoundfx(4);
								
								latag[26]=true;	
								match2deck1[26]=true;
								match2deck2[26]=true;
								match2deck3[26]=true;
								p=4;
							} else
								card27.setImageResource(valueCard(slottedDeck[26]));
						}
						for (p=0;p<4;p++)  {
							if (matchbox[p]==slottedDeck[27]) {
								card28.setImageResource(valueCard(slottedDeck[27]+52));
							    	
							        myObjectRotate28.setFillAfter(true);
							        myObjectRotate28.setAnimationListener(new AnimationListener() {
						        	public void onAnimationEnd(Animation _animation) {
							        		//card28.startAnimation(in);
							        	}
							        	public void onAnimationRepeat(Animation _animation) {}
							        	public void onAnimationStart(Animation _animation) {}
							        });
							        card28.startAnimation(myObjectRotate28); mysoundfx(4);
								
								latag[27]=true;
								match2deck1[27]=true;
								match2deck2[27]=true;
								match2deck3[27]=true;
								p=4;
							} else
								card28.setImageResource(valueCard(slottedDeck[27]));
						}
						//2nd layer
						if (activc16==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[15]) {
									card16.setImageResource(valueCard(slottedDeck[15]+52));
							        	myObjectRotate16.setFillAfter(true);
							        	myObjectRotate16.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card16.startAnimation(myObjectRotate16); mysoundfx(4);												
									
									latag[15]=true;
									match2deck1[15]=true;
									match2deck2[15]=true;
									match2deck3[15]=true;
									p=4;
								} else
									card16.setImageResource(valueCard(slottedDeck[15]));
							}
						}

						if (activc17==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[16]) {
									card17.setImageResource(valueCard(slottedDeck[16]+52));
							        	myObjectRotate17.setFillAfter(true);
							        	myObjectRotate17.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card17.startAnimation(myObjectRotate17); mysoundfx(4);												
									
									latag[16]=true;
									match2deck1[16]=true;
									match2deck2[16]=true;
									match2deck3[16]=true;
									p=4;
								} else
									card17.setImageResource(valueCard(slottedDeck[16]));
							}
						}

						if (activc18==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[17]) {
									card18.setImageResource(valueCard(slottedDeck[17]+52));
							        	myObjectRotate18.setFillAfter(true);
							        	myObjectRotate18.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card18.startAnimation(myObjectRotate18); mysoundfx(4);												
									
									latag[17]=true;
									match2deck1[17]=true;
									match2deck2[17]=true;
									match2deck3[17]=true;
									p=4;
								} else
									card18.setImageResource(valueCard(slottedDeck[17]));
							}
						}
						if (activc19==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[18]) {
									card19.setImageResource(valueCard(slottedDeck[18]+52));
							        	myObjectRotate19.setFillAfter(true);
							        	myObjectRotate19.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card19.startAnimation(myObjectRotate19); mysoundfx(4);												
									
									latag[18]=true;
									match2deck1[18]=true;
									match2deck2[18]=true;
									match2deck3[18]=true;
									p=4;
								} else
									card19.setImageResource(valueCard(slottedDeck[18]));
							}
						}
						if (activc20==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[19]) {
									card20.setImageResource(valueCard(slottedDeck[19]+52));
							        	myObjectRotate20.setFillAfter(true);
							        	myObjectRotate20.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card20.startAnimation(myObjectRotate20); mysoundfx(4);												
									
									latag[19]=true;
									match2deck1[19]=true;
									match2deck2[19]=true;
									match2deck3[19]=true;
									p=4;
								} else
									card20.setImageResource(valueCard(slottedDeck[19]));
							}
						}
						if (activc21==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[20]) {
									card21.setImageResource(valueCard(slottedDeck[20]+52));
							        	myObjectRotate21.setFillAfter(true);
							        	myObjectRotate21.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card21.startAnimation(myObjectRotate21); mysoundfx(4);												
									
									latag[20]=true;
									match2deck1[20]=true;
									match2deck2[20]=true;
									match2deck3[20]=true;
									p=4;
								} else
									card21.setImageResource(valueCard(slottedDeck[20]));
							}
						}
						//3rd layer
						if (activc11==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[10]) {
									card11.setImageResource(valueCard(slottedDeck[10]+52));
							        	myObjectRotate11.setFillAfter(true);
							        	myObjectRotate11.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card11.startAnimation(myObjectRotate11); mysoundfx(4);												
									
									latag[10]=true;
									match2deck1[10]=true;
									match2deck2[10]=true;
									match2deck3[10]=true;
									p=4;
								} else
									card11.setImageResource(valueCard(slottedDeck[10]));
							}
						}

						if (activc12==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[11]) {
									card12.setImageResource(valueCard(slottedDeck[11]+52));
							        	myObjectRotate12.setFillAfter(true);
							        	myObjectRotate12.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card12.startAnimation(myObjectRotate12); mysoundfx(4);												
									
									latag[11]=true;
									match2deck1[11]=true;
									match2deck2[11]=true;
									match2deck3[11]=true;
									p=4;
								} else
									card12.setImageResource(valueCard(slottedDeck[11]));
							}
						}

						if (activc13==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[12]) {
									card13.setImageResource(valueCard(slottedDeck[12]+52));
							        	myObjectRotate13.setFillAfter(true);
							        	myObjectRotate13.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card13.startAnimation(myObjectRotate13); mysoundfx(4);												
									
									latag[12]=true;
									match2deck1[12]=true;
									match2deck2[12]=true;
									match2deck3[12]=true;
									p=4;
								} else
									card13.setImageResource(valueCard(slottedDeck[12]));
							}
						}
						if (activc14==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[13]) {
									card14.setImageResource(valueCard(slottedDeck[13]+52));
							        	myObjectRotate14.setFillAfter(true);
							        	myObjectRotate14.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card14.startAnimation(myObjectRotate14); mysoundfx(4);												
									
									latag[13]=true;
									match2deck1[13]=true;
									match2deck2[13]=true;
									match2deck3[13]=true;
									p=4;
								} else
									card14.setImageResource(valueCard(slottedDeck[13]));
							}
						}
						if (activc15==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[14]) {
									card15.setImageResource(valueCard(slottedDeck[14]+52));
							        	myObjectRotate15.setFillAfter(true);
							        	myObjectRotate15.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card15.startAnimation(myObjectRotate15); mysoundfx(4);												
									
									latag[14]=true;
									match2deck1[14]=true;
									match2deck2[14]=true;
									match2deck3[14]=true;
									p=4;
								} else
									card15.setImageResource(valueCard(slottedDeck[14]));
							}
						}
						//4th layer
						if (activc7==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[6]) {
									card7.setImageResource(valueCard(slottedDeck[6]+52));
							        	myObjectRotate7.setFillAfter(true);
							        	myObjectRotate7.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card7.startAnimation(myObjectRotate7); mysoundfx(4);												
									
									latag[6]=true;
									match2deck1[6]=true;
									match2deck2[6]=true;
									match2deck3[6]=true;
									p=4;
								} else
									card7.setImageResource(valueCard(slottedDeck[6]));
							}
						}
						
						if (activc8==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[7]) {
									card8.setImageResource(valueCard(slottedDeck[7]+52));
							        	myObjectRotate8.setFillAfter(true);
							        	myObjectRotate8.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card8.startAnimation(myObjectRotate8); mysoundfx(4);												
									
									latag[7]=true;
									match2deck1[7]=true;
									match2deck2[7]=true;
									match2deck3[7]=true;
									p=4;
								} else
									card8.setImageResource(valueCard(slottedDeck[7]));
							}
						}
						if (activc9==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[8]) {
									card9.setImageResource(valueCard(slottedDeck[8]+52));
							        	myObjectRotate9.setFillAfter(true);
							        	myObjectRotate9.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card9.startAnimation(myObjectRotate9); mysoundfx(4);												
									
									latag[8]=true;
									match2deck1[8]=true;
									match2deck2[8]=true;
									match2deck3[8]=true;
									p=4;
								} else
									card9.setImageResource(valueCard(slottedDeck[8]));
							}
						}

						if (activc10==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[9]) {
									card10.setImageResource(valueCard(slottedDeck[9]+52));
							        	myObjectRotate10.setFillAfter(true);
							        	myObjectRotate10.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card10.startAnimation(myObjectRotate10); mysoundfx(4);												
									
									latag[9]=true;
									match2deck1[9]=true;
									match2deck2[9]=true;
									match2deck3[9]=true;
									p=4;
								} else
									card10.setImageResource(valueCard(slottedDeck[9]));
							}
						}
						// 5th layer
						if (activc6==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[5]) {
									card6.setImageResource(valueCard(slottedDeck[5]+52));
							        	myObjectRotate6.setFillAfter(true);
							        	myObjectRotate6.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card6.startAnimation(myObjectRotate6); mysoundfx(4);												
									
									latag[5]=true;
									match2deck1[5]=true;
									match2deck2[5]=true;
									match2deck3[5]=true;
									p=4;
								} else
									card6.setImageResource(valueCard(slottedDeck[5]));
							}
						}
						if (activc5==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[4]) {
									card5.setImageResource(valueCard(slottedDeck[4]+52));
							        	myObjectRotate5.setFillAfter(true);
							        	myObjectRotate5.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card5.startAnimation(myObjectRotate5); mysoundfx(4);												
									
									latag[4]=true;
									match2deck1[4]=true;
									match2deck2[4]=true;
									match2deck3[4]=true;
									p=4;
								} else
									card5.setImageResource(valueCard(slottedDeck[4]));
							}
						}
						if (activc4==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[3]) {
									card4.setImageResource(valueCard(slottedDeck[3]+52));
							        	myObjectRotate4.setFillAfter(true);
							        	myObjectRotate4.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card4.startAnimation(myObjectRotate4); mysoundfx(4);													
																				
									latag[3]=true;
									match2deck1[3]=true;
									match2deck2[3]=true;
									match2deck3[3]=true;
									p=4;
								} else
									card4.setImageResource(valueCard(slottedDeck[3]));
							}
						}
						//6th layer
						
						if (activc2==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[1]) {
									card2.setImageResource(valueCard(slottedDeck[1]+52));
							        	myObjectRotate2.setFillAfter(true);
							        	myObjectRotate2.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card2.startAnimation(myObjectRotate2); mysoundfx(4);												
									
									latag[1]=true;
									match2deck1[1]=true;
									match2deck2[1]=true;
									match2deck3[1]=true;
									p=4;
								} else
									card2.setImageResource(valueCard(slottedDeck[1]));
							}
						}
						if (activc3==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[2]) {
									card3.setImageResource(valueCard(slottedDeck[2]+52));
							        	myObjectRotate3.setFillAfter(true);
							        	myObjectRotate3.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card3.startAnimation(myObjectRotate3); mysoundfx(4);												
									
									latag[2]=true;
									match2deck1[2]=true;
									match2deck2[2]=true;
									match2deck3[2]=true;
									p=4;
								} else
									card3.setImageResource(valueCard(slottedDeck[2]));
							}
						}
						//7th layer
						if (activc1==true)	{
							for (p=0;p<4;p++)  {
								if (matchbox[p]==slottedDeck[0]) {
									card1.setImageResource(valueCard(slottedDeck[0]+52));
							        	myObjectRotate1.setFillAfter(true);
							        	myObjectRotate1.setAnimationListener(new AnimationListener() {
							        		public void onAnimationEnd(Animation _animation) {}
							        		public void onAnimationRepeat(Animation _animation) {}
							        		public void onAnimationStart(Animation _animation) {}
							        	});
						        		card1.startAnimation(myObjectRotate1); mysoundfx(4);												
									
									latag[0]=true;
									match2deck1[0]=true;
									match2deck2[0]=true;
									match2deck3[0]=true;
									p=4;
								} else
									card1.setImageResource(valueCard(slottedDeck[0]));
							}
						}
						
						}
					} else 
						dealDeck1.setImageResource(valueCard(slottedDeck[next2jkatch]));
				}
			});

		} else if ((match2deck2[card_no-1]==true)&&(latag[card_no-1]==false)) {
			match2deck2[card_no-1]=false;
			match2deck3[card_no-1]=false;
		}
	}
	

	public void checkgoldkey(int card_no, int p,int katch, int[] matchbox) {
		goldvalues[0]=-1;
		if (goldkeyOn==true) {
			int it=0,ni=0;
			boolean maynagmatch=false;
			goldkeyOn=false;					
			openbygoldkey=true;
			for (it=0;it<28;it++) {
				match2deck1[it]=false;
				match2deck2[it]=false;
				match2deck3[it]=false;	
			}
			for (it=0;it<23;it++) {
				for (p=0;p<4;p++)  {
					if (matchbox[p]==slottedDeck[it]) {
						goldvalues[0]=it;
						ni++;
						p=4;
						maynagmatch=true;
						it=23;		
					}
				}

			}
			matchcards[0]=katch;
			if (goldvalues[0]!=-1) {
				if ((slottedDeck[goldvalues[0]]<104) && (card_no!=goldvalues[0]+1)){ 
					openbygold[goldvalues[0]]=true;
					}
			}
	 		switch (goldvalues[0]) {
					case(0)	:card1.setImageResource(valueCard(slottedDeck[0]+52));activc1=true;break;
					case(1)	:card2.setImageResource(valueCard(slottedDeck[1]+52));activc2=true;break;
					case(2)	:card3.setImageResource(valueCard(slottedDeck[2]+52));activc3=true;break;
					case(3)	:card4.setImageResource(valueCard(slottedDeck[3]+52));activc4=true;break;
					case(4)	:card5.setImageResource(valueCard(slottedDeck[4]+52));activc5=true;break;
					case(5)	:card6.setImageResource(valueCard(slottedDeck[5]+52));activc6=true;break;
					case(6)	:card7.setImageResource(valueCard(slottedDeck[6]+52));activc7=true;break;
					case(7)	:card8.setImageResource(valueCard(slottedDeck[7]+52));activc8=true;break;
					case(8)	:card9.setImageResource(valueCard(slottedDeck[8]+52));activc9=true;break;
					case(9)	:card10.setImageResource(valueCard(slottedDeck[9]+52));activc10=true;break;
					case(10) :card11.setImageResource(valueCard(slottedDeck[10]+52));activc11=true;break;	
					case(11) :card12.setImageResource(valueCard(slottedDeck[11]+52));activc12=true;break;	
					case(12) :card13.setImageResource(valueCard(slottedDeck[12]+52));activc13=true;break;	
					case(13) :card14.setImageResource(valueCard(slottedDeck[13]+52));activc14=true;break;	
					case(14) :card15.setImageResource(valueCard(slottedDeck[14]+52));activc15=true;break;	
					case(15) :card16.setImageResource(valueCard(slottedDeck[15]+52));activc16=true;break;	
					case(16) :card17.setImageResource(valueCard(slottedDeck[16]+52));activc17=true;break;	
					case(17) :card18.setImageResource(valueCard(slottedDeck[17]+52));activc18=true;break;	
					case(18) :card19.setImageResource(valueCard(slottedDeck[18]+52));activc19=true;break;	
					case(19) :card20.setImageResource(valueCard(slottedDeck[19]+52));activc20=true;break;	
					case(20) :card21.setImageResource(valueCard(slottedDeck[20]+52));activc21=true;break;
					case(21) :card22.setImageResource(valueCard(slottedDeck[21]+52));activc22=true;break;	
					case(22) :card23.setImageResource(valueCard(slottedDeck[22]+52));activc23=true;break;
			}
	 		if (maynagmatch==false) {
	 			goldkey.setImageResource(R.drawable.goldkey);
	 			openbygoldkey=false;
	 		}
	 		goldvalues[0]=-1;
		}	

	}
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final LinearLayout dealArea = (LinearLayout)findViewById(R.id.LinearLayout10);
		
		int[][] cardDeck = new int[4][13];
			
		 scoreArea = (LinearLayout)findViewById(R.id.LinearLayout02);
		 card1 = (ImageView)findViewById(R.id.ImageView01);
		 card2 = (ImageView)findViewById(R.id.ImageView02);
		 card3 = (ImageView)findViewById(R.id.ImageView03);
		 card4 = (ImageView)findViewById(R.id.ImageView04);
		 card5 = (ImageView)findViewById(R.id.ImageView05);
		 card6 = (ImageView)findViewById(R.id.ImageView06);
		 card7 = (ImageView)findViewById(R.id.ImageView07);
		 card8 = (ImageView)findViewById(R.id.ImageView08);
		 card9 = (ImageView)findViewById(R.id.ImageView09);
		 card10 = (ImageView)findViewById(R.id.ImageView10);
		 card11 = (ImageView)findViewById(R.id.ImageView11);
		 card12 = (ImageView)findViewById(R.id.ImageView12);
		 card13 = (ImageView)findViewById(R.id.ImageView13);
		 card14 = (ImageView)findViewById(R.id.ImageView14);
		 card15 = (ImageView)findViewById(R.id.ImageView15);
		 card16 = (ImageView)findViewById(R.id.ImageView16);
		 card17 = (ImageView)findViewById(R.id.ImageView17);
		 card18 = (ImageView)findViewById(R.id.ImageView18);
		 card19 = (ImageView)findViewById(R.id.ImageView19);
		 card20 = (ImageView)findViewById(R.id.ImageView20);
		 card21 = (ImageView)findViewById(R.id.ImageView21);
		 card22 = (ImageView)findViewById(R.id.ImageView22);
		 card23 = (ImageView)findViewById(R.id.ImageView23);
		 card24 = (ImageView)findViewById(R.id.ImageView24);
		 card25 = (ImageView)findViewById(R.id.ImageView25);
		 card26 = (ImageView)findViewById(R.id.ImageView26);
		 card27 = (ImageView)findViewById(R.id.ImageView27);
		 card28 = (ImageView)findViewById(R.id.ImageView28);
		 card29 = (ImageView)findViewById(R.id.ImageView29);
		 
		 mChronometer = new Chronometer(this);
		 mChronometer.start();

		goldvalues[0]=-1;
		myObjectRotate1 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate2 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate3 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate4 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate5 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate6 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate7 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate8 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate9 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate10 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate11 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate12 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate13 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate14 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate15 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate16 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate17 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate18 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate19 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate20 = AnimationUtils.loadAnimation(this, R.anim.rotate);
		myObjectRotate21 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	myObjectRotate22 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	myObjectRotate23 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	myObjectRotate24 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	myObjectRotate25 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	myObjectRotate26 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	myObjectRotate27 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	myObjectRotate28 = AnimationUtils.loadAnimation(this, R.anim.rotate);
	  	
	  	myshuffle = new soundlife();
	  	myshuffle.initSounds(getBaseContext());
	  	myshuffle.addSound(1, R.raw.shuffle);	  	
	  	mymagicw = new soundlife();
	  	mymagicw.initSounds(getBaseContext());
	  	mymagicw.addSound(1, R.raw.magicwand);
	  	mypageturn = new soundlife();
	  	mypageturn.initSounds(getBaseContext());
	  	mypageturn.addSound(1, R.raw.pageturn);
	  	mycrowd = new soundlife();
	  	mycrowd.initSounds(getBaseContext());
	  	mycrowd.addSound(1, R.raw.crowdcheer);
	  	mysiren = new soundlife();
	  	mysiren.initSounds(getBaseContext());
	  	mysiren.addSound(1, R.raw.sirenwail);


		dealDeck1 = new ImageView(this);
		dealDeck2 = new ImageView(this);
		dealDeck3 = new ImageView(this);
		onesdigit = new ImageView(this);
		tenthsdigit = new ImageView(this);
		hundredsdigit = new ImageView(this);
		int i=0,j=0,k=0,cvalue=0,slotnum;
		Random rslot = new Random(); 
		final ImageView scorecard = (ImageView)findViewById(R.id.scoreView);
		goldkey = (ImageView)findViewById(R.id.ImageView30);
		greenkey = (ImageView)findViewById(R.id.ImageView31);
		numgoldkey = (ImageView)findViewById(R.id.ImageView34);
		numgreenkey = (ImageView)findViewById(R.id.ImageView35);
		final ImageView keybutton = (ImageView)findViewById(R.id.keyButt);
		final ImageView playtimebutton = (ImageView)findViewById(R.id.timeButt);
		scoreArea.removeView(scorecard);
		onesdigit.setImageResource(valueScoreCard(scores));
		onesdigit.setPadding(135,0,0,0);
		scoreArea.addView(onesdigit);
		// initialize arrays
		
		for (i=0;i<28;i++) {
			match2deck1[i]=false;
			match2deck2[i]=false;
			match2deck3[i]=false;
			openbygold[i]=false;
			flipone[i]=false;
		}

		//populate the arrays
		// cvalue is from 0 to 51;
		for (i=0;i<4;i++) {
			for (j=0;j<13;j++) {
				cardDeck[i][j]=cvalue;
				slottedDeck[cvalue]=-1;
				clickna[cvalue]=false;		
				cvalue++;
			}
		};
		matchcards[0]=-1;
		updateScore(scores); matchcards[1]=-1; counterMcard(2);  closedbygold(1);

		// shuffle 52 cards
		while (k<52) {
			slotnum = rslot.nextInt(52); // 0 to 51,
			if (slottedDeck[slotnum]==-1) {
				slottedDeck[slotnum]=k;
				k++;
			}
			
		}

		numgoldkey.setImageResource(keyNum(goldkeybilang));
		numgreenkey.setImageResource(keyNum(greenkeybilang));
		//open cards by default
		int open3=23,open4=24,open5=25,open6=26,open7=27;
		
		card24.setImageResource(valueCard(slottedDeck[open3]));
		card25.setImageResource(valueCard(slottedDeck[open4]));
		card26.setImageResource(valueCard(slottedDeck[open5]));
		card27.setImageResource(valueCard(slottedDeck[open6]));
		card28.setImageResource(valueCard(slottedDeck[open7]));

		keybutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
                final Dialog rdialog = new Dialog(myMoishdActivity.this);
                rdialog.setContentView(R.layout.myredeem);
                rdialog.setTitle("Choose Key to Exchange");
                rdialog.setCancelable(true);
                final RadioButton rb1;
			    final RadioButton rb2;
                Button redeembtn1=(Button)rdialog.findViewById(R.id.RedeemButton01);
                rb1=(RadioButton)rdialog.findViewById(R.id.goldRadioButton01);
                rb2=(RadioButton)rdialog.findViewById(R.id.greenRadioButton01);
                rdialog.show();
                redeembtn1.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (rb1.isChecked()==true) {
							if (scores-200 >= 0) {
								goldkeybilang++;
								scores=scores-200;
								scoreArea.removeView(hundredsdigit);
								scoreArea.removeView(tenthsdigit);
								scoreArea.removeView(onesdigit);
								hundredsna=false;
								if (scores>=10) { 
									ones=valueScoreCard(scores%10);
									tenths=valueScoreCard(scores/10);
									onesdigit.setImageResource(ones);
									tenthsdigit.setImageResource(tenths);
									tenthsdigit.setPadding(95,0,0,0); onesdigit.setPadding(0,0,0,0);
									scoreArea.addView(tenthsdigit);
									scoreArea.addView(onesdigit);
									if (scores/10>9) {
										hundreds=valueScoreCard((scores/10)/10);
										tenths=valueScoreCard((scores/10)%10);
										if (hundredsna==false) {
											scoreArea.removeView(onesdigit);
											scoreArea.removeView(tenthsdigit);										
											tenthsdigit.setPadding(0,0,0,0);
											hundredsdigit.setPadding(55,0,0,0);	
											scoreArea.addView(hundredsdigit);
											scoreArea.addView(tenthsdigit);
											scoreArea.addView(onesdigit);
											hundredsna=true;
										}
										hundredsdigit.setImageResource(hundreds);
									}
									onesdigit.setImageResource(ones);
									tenthsdigit.setImageResource(tenths);
								} else  {
											ones=valueScoreCard(scores%10);											
											onesdigit.setPadding(135,0,0,0);
											onesdigit.setImageResource(ones); 
											scoreArea.addView(onesdigit);
											zeroremoved=false;
										} 								

							} else
								Toast.makeText(v.getContext(), "Not Enough Points!", Toast.LENGTH_LONG).show();
							numgoldkey.setImageResource(keyNum(goldkeybilang));
							
						}
						if (rb2.isChecked()==true) {
							if (scores-100 >= 0) {
								greenkeybilang++;
								scores=scores-100;
								scoreArea.removeView(hundredsdigit);
								scoreArea.removeView(tenthsdigit);
								scoreArea.removeView(onesdigit);
								hundredsna=false;
								if (scores>=10) { 
									ones=valueScoreCard(scores%10);
									tenths=valueScoreCard(scores/10);
									onesdigit.setImageResource(ones);
									tenthsdigit.setImageResource(tenths);
									tenthsdigit.setPadding(95,0,0,0); onesdigit.setPadding(0,0,0,0);
									scoreArea.addView(tenthsdigit);
									scoreArea.addView(onesdigit);
									if (scores/10>9) {
										hundreds=valueScoreCard((scores/10)/10);
										tenths=valueScoreCard((scores/10)%10);
										if (hundredsna==false) {
											scoreArea.removeView(onesdigit);
											scoreArea.removeView(tenthsdigit);										
											tenthsdigit.setPadding(0,0,0,0);
											hundredsdigit.setPadding(55,0,0,0);	
											scoreArea.addView(hundredsdigit);
											scoreArea.addView(tenthsdigit);
											scoreArea.addView(onesdigit);
											hundredsna=true;
										}
										hundredsdigit.setImageResource(hundreds);
									}
									onesdigit.setImageResource(ones);
									tenthsdigit.setImageResource(tenths);
								} else  {
											ones=valueScoreCard(scores%10);											
											onesdigit.setPadding(135,0,0,0);
											onesdigit.setImageResource(ones); 
											scoreArea.addView(onesdigit);
											zeroremoved=false;
										} 

							} else
								Toast.makeText(v.getContext(), "Not Enough Points!", Toast.LENGTH_LONG).show();
							numgreenkey.setImageResource(keyNum(greenkeybilang));
							
						}
						rdialog.cancel();
					}
				});
                v.refreshDrawableState();
                
			}
		});
		
		playtimebutton.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				long pseconds=0,truesec=0;
				long pminutes=0;
				String isec;
				// TODO Auto-generated method stub
				
				long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
	            pseconds=elapsedMillis/1000;
	            pminutes=(pseconds/60)+ ((pseconds-((pseconds/60)*60))/60);
	            truesec=((pseconds-((pseconds/60)*60))%60);
	            if (truesec<10) {
	            	isec="0"; 
	            }
	            	else 
	            		isec="";
	            elapsedplay=pminutes+"m:"+isec+truesec+"s";
	            Toast.makeText(myMoishdActivity.this, "Elapsed Time: " + pminutes +"m:"+isec+truesec+"s", 
		    	            Toast.LENGTH_LONG).show(); 

			}
		});

		goldkey.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (goldkeybilang>0) {
					mysoundfx(5);
					goldkey.setImageResource(R.drawable.hgoldkey);
					goldkeyOn=true;
				}
			}
		});
		
		greenkey.setOnClickListener(new View.OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  	
			  if (greenkeybilang>0) {
				mysoundfx(5);
				greenkey.setImageResource(R.drawable.hgreenkey);
				gipit=true;
				if (facedup==true) {
					facedup=false;
					dealDeck1.setOnClickListener(new View.OnClickListener() {
			
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int next2jkatch=jkatch-2;
//							if (slottedDeck[jkatch-1]==104) {
							goldkeyOn=false;
								if (gipit==true)  {
									int p=0,mkatch=0;
									int[] matchbox = new int[4];
									int ht=0;
									for (ht=0;ht<52;ht++) {
										clickna[ht]=false;
									}
									mkatch=slottedDeck[next2jkatch];
									matchbox=tableCard(mkatch);
									if (mkatch!=104) {
										dealDeck1.setImageResource(valueCard(slottedDeck[next2jkatch]+52));
									}
									if (activc22==true) {
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[21]) {
												card22.setImageResource(valueCard(slottedDeck[21]+52));
												myObjectRotate22.setFillAfter(true);
											        myObjectRotate22.setAnimationListener(new AnimationListener() {
											        	public void onAnimationEnd(Animation _animation) {}
											        	public void onAnimationRepeat(Animation _animation) {}
											        	public void onAnimationStart(Animation _animation) {}
											        });
										        	card22.startAnimation(myObjectRotate22); mysoundfx(4);
												latag[21]=true;
												match2deck1[21]=true;
												match2deck2[21]=true;
												match2deck3[21]=true;
												p=4;
											} else
												card22.setImageResource(valueCard(slottedDeck[21]));
										}
									}
									if (activc23==true) {	
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[22]) {
												card23.setImageResource(valueCard(slottedDeck[22]+52));
												
												myObjectRotate23.setFillAfter(true);
										        	myObjectRotate23.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
											        	public void onAnimationStart(Animation _animation) {}
											        });
											        card23.startAnimation(myObjectRotate23); mysoundfx(4);
												latag[22]=true;
												match2deck1[22]=true;
												match2deck2[22]=true;
												match2deck3[22]=true;
												p=4;
											} else
												card23.setImageResource(valueCard(slottedDeck[22]));
										}
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[23]) {
											card24.setImageResource(valueCard(slottedDeck[23]+52));
										    	
									      		myObjectRotate24.setFillAfter(true);
									        	myObjectRotate24.setAnimationListener(new AnimationListener() {
									        		public void onAnimationEnd(Animation _animation) {}
										        	public void onAnimationRepeat(Animation _animation) {}
										        	public void onAnimationStart(Animation _animation) {}
									        	});
									        	card24.startAnimation(myObjectRotate24); mysoundfx(4);	
																						
											latag[23]=true;
											match2deck1[23]=true;
											match2deck2[23]=true;
											match2deck3[23]=true;
											p=4;	
										} else
											card24.setImageResource(valueCard(slottedDeck[23]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[24]) {
											card25.setImageResource(valueCard(slottedDeck[24]+52));
										    	
										        myObjectRotate25.setFillAfter(true);
										        myObjectRotate25.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
										        		//card25.startAnimation(in);
										        	}
										        	public void onAnimationRepeat(Animation _animation) {}
										        	public void onAnimationStart(Animation _animation) {}
										        });
										        card25.startAnimation(myObjectRotate25); mysoundfx(4);
											
											latag[24]=true;
											match2deck1[24]=true;
											match2deck2[24]=true;
											match2deck3[24]=true;
											p=4;
										} else
											card25.setImageResource(valueCard(slottedDeck[24]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[25]) {
											card26.setImageResource(valueCard(slottedDeck[25]+52));
										    	
										        myObjectRotate26.setFillAfter(true);
										        myObjectRotate26.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
										        		//card26.startAnimation(in);
										        	}
										        	public void onAnimationRepeat(Animation _animation) {}
										        	public void onAnimationStart(Animation _animation) {}
										        });
										        card26.startAnimation(myObjectRotate26); mysoundfx(4);
											
											latag[25]=true;
											match2deck1[25]=true;
											match2deck2[25]=true;
											match2deck3[25]=true;
											p=4;
										} else
											card26.setImageResource(valueCard(slottedDeck[25]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[26]) {
											card27.setImageResource(valueCard(slottedDeck[26]+52));
										    	
										        myObjectRotate27.setFillAfter(true);
										        myObjectRotate27.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
										        		//card27.startAnimation(in);
										        	}
										        	public void onAnimationRepeat(Animation _animation) {}
										        	public void onAnimationStart(Animation _animation) {}
										        });
										        card27.startAnimation(myObjectRotate27); mysoundfx(4);
											
											latag[26]=true;	
											match2deck1[26]=true;
											match2deck2[26]=true;
											match2deck3[26]=true;
											p=4;
										} else
											card27.setImageResource(valueCard(slottedDeck[26]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[27]) {
											card28.setImageResource(valueCard(slottedDeck[27]+52));
										    	
										        myObjectRotate28.setFillAfter(true);
										        myObjectRotate28.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
										        		//card28.startAnimation(in);
										        	}
										        	public void onAnimationRepeat(Animation _animation) {}
										        	public void onAnimationStart(Animation _animation) {}
										        });
										        card28.startAnimation(myObjectRotate28); mysoundfx(4);
											
											latag[27]=true;
											match2deck1[27]=true;
											match2deck2[27]=true;
											match2deck3[27]=true;
											p=4;
										} else
											card28.setImageResource(valueCard(slottedDeck[27]));
									}
									//2nd layer
									if (activc16==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[15]) {
												card16.setImageResource(valueCard(slottedDeck[15]+52));
										        	myObjectRotate16.setFillAfter(true);
										        	myObjectRotate16.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card16.startAnimation(myObjectRotate16); mysoundfx(4);												
												
												latag[15]=true;
												match2deck1[15]=true;
												match2deck2[15]=true;
												match2deck3[15]=true;
												p=4;
											} else
												card16.setImageResource(valueCard(slottedDeck[15]));
										}
									}

									if (activc17==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[16]) {
												card17.setImageResource(valueCard(slottedDeck[16]+52));
										        	myObjectRotate17.setFillAfter(true);
										        	myObjectRotate17.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card17.startAnimation(myObjectRotate17); mysoundfx(4);												
												
												latag[16]=true;
												match2deck1[16]=true;
												match2deck2[16]=true;
												match2deck3[16]=true;
												p=4;
											} else
												card17.setImageResource(valueCard(slottedDeck[16]));
										}
									}

									if (activc18==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[17]) {
												card18.setImageResource(valueCard(slottedDeck[17]+52));
										        	myObjectRotate18.setFillAfter(true);
										        	myObjectRotate18.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card18.startAnimation(myObjectRotate18); mysoundfx(4);												
												
												latag[17]=true;
												match2deck1[17]=true;
												match2deck2[17]=true;
												match2deck3[17]=true;
												p=4;
											} else
												card18.setImageResource(valueCard(slottedDeck[17]));
										}
									}
									if (activc19==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[18]) {
												card19.setImageResource(valueCard(slottedDeck[18]+52));
										        	myObjectRotate19.setFillAfter(true);
										        	myObjectRotate19.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card19.startAnimation(myObjectRotate19); mysoundfx(4);												
												
												latag[18]=true;
												match2deck1[18]=true;
												match2deck2[18]=true;
												match2deck3[18]=true;
												p=4;
											} else
												card19.setImageResource(valueCard(slottedDeck[18]));
										}
									}
									if (activc20==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[19]) {
												card20.setImageResource(valueCard(slottedDeck[19]+52));
										        	myObjectRotate20.setFillAfter(true);
										        	myObjectRotate20.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card20.startAnimation(myObjectRotate20); mysoundfx(4);												
												
												latag[19]=true;
												match2deck1[19]=true;
												match2deck2[19]=true;
												match2deck3[19]=true;
												p=4;
											} else
												card20.setImageResource(valueCard(slottedDeck[19]));
										}
									}
									if (activc21==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[20]) {
												card21.setImageResource(valueCard(slottedDeck[20]+52));
										        	myObjectRotate21.setFillAfter(true);
										        	myObjectRotate21.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card21.startAnimation(myObjectRotate21); mysoundfx(4);												
												
												latag[20]=true;
												match2deck1[20]=true;
												match2deck2[20]=true;
												match2deck3[20]=true;
												p=4;
											} else
												card21.setImageResource(valueCard(slottedDeck[20]));
										}
									}
									//3rd layer
									if (activc11==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[10]) {
												card11.setImageResource(valueCard(slottedDeck[10]+52));
										        	myObjectRotate11.setFillAfter(true);
										        	myObjectRotate11.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card11.startAnimation(myObjectRotate11); mysoundfx(4);												
												
												latag[10]=true;
												match2deck1[10]=true;
												match2deck2[10]=true;
												match2deck3[10]=true;
												p=4;
											} else
												card11.setImageResource(valueCard(slottedDeck[10]));
										}
									}

									if (activc12==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[11]) {
												card12.setImageResource(valueCard(slottedDeck[11]+52));
										        	myObjectRotate12.setFillAfter(true);
										        	myObjectRotate12.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card12.startAnimation(myObjectRotate12); mysoundfx(4);												
												
												latag[11]=true;
												match2deck1[11]=true;
												match2deck2[11]=true;
												match2deck3[11]=true;
												p=4;
											} else
												card12.setImageResource(valueCard(slottedDeck[11]));
										}
									}

									if (activc13==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[12]) {
												card13.setImageResource(valueCard(slottedDeck[12]+52));
										        	myObjectRotate13.setFillAfter(true);
										        	myObjectRotate13.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card13.startAnimation(myObjectRotate13); mysoundfx(4);												
												
												latag[12]=true;
												match2deck1[12]=true;
												match2deck2[12]=true;
												match2deck3[12]=true;
												p=4;
											} else
												card13.setImageResource(valueCard(slottedDeck[12]));
										}
									}
									if (activc14==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[13]) {
												card14.setImageResource(valueCard(slottedDeck[13]+52));
										        	myObjectRotate14.setFillAfter(true);
										        	myObjectRotate14.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card14.startAnimation(myObjectRotate14); mysoundfx(4);												
												
												latag[13]=true;
												match2deck1[13]=true;
												match2deck2[13]=true;
												match2deck3[13]=true;
												p=4;
											} else
												card14.setImageResource(valueCard(slottedDeck[13]));
										}
									}
									if (activc15==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[14]) {
												card15.setImageResource(valueCard(slottedDeck[14]+52));
										        	myObjectRotate15.setFillAfter(true);
										        	myObjectRotate15.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card15.startAnimation(myObjectRotate15); mysoundfx(4);												
												
												latag[14]=true;
												match2deck1[14]=true;
												match2deck2[14]=true;
												match2deck3[14]=true;
												p=4;
											} else
												card15.setImageResource(valueCard(slottedDeck[14]));
										}
									}
									//4th layer
									if (activc7==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[6]) {
												card7.setImageResource(valueCard(slottedDeck[6]+52));
										        	myObjectRotate7.setFillAfter(true);
										        	myObjectRotate7.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card7.startAnimation(myObjectRotate7); mysoundfx(4);												
												
												latag[6]=true;
												match2deck1[6]=true;
												match2deck2[6]=true;
												match2deck3[6]=true;
												p=4;
											} else
												card7.setImageResource(valueCard(slottedDeck[6]));
										}
									}
									
									if (activc8==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[7]) {
												card8.setImageResource(valueCard(slottedDeck[7]+52));
										        	myObjectRotate8.setFillAfter(true);
										        	myObjectRotate8.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card8.startAnimation(myObjectRotate8); mysoundfx(4);												
												
												latag[7]=true;
												match2deck1[7]=true;
												match2deck2[7]=true;
												match2deck3[7]=true;
												p=4;
											} else
												card8.setImageResource(valueCard(slottedDeck[7]));
										}
									}
									if (activc9==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[8]) {
												card9.setImageResource(valueCard(slottedDeck[8]+52));
										        	myObjectRotate9.setFillAfter(true);
										        	myObjectRotate9.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card9.startAnimation(myObjectRotate9); mysoundfx(4);												
												
												latag[8]=true;
												match2deck1[8]=true;
												match2deck2[8]=true;
												match2deck3[8]=true;
												p=4;
											} else
												card9.setImageResource(valueCard(slottedDeck[8]));
										}
									}

									if (activc10==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[9]) {
												card10.setImageResource(valueCard(slottedDeck[9]+52));
										        	myObjectRotate10.setFillAfter(true);
										        	myObjectRotate10.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card10.startAnimation(myObjectRotate10); mysoundfx(4);												
												
												latag[9]=true;
												match2deck1[9]=true;
												match2deck2[9]=true;
												match2deck3[9]=true;
												p=4;
											} else
												card10.setImageResource(valueCard(slottedDeck[9]));
										}
									}
									// 5th layer
									if (activc6==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[5]) {
												card6.setImageResource(valueCard(slottedDeck[5]+52));
										        	myObjectRotate6.setFillAfter(true);
										        	myObjectRotate6.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card6.startAnimation(myObjectRotate6); mysoundfx(4);												
												
												latag[5]=true;
												match2deck1[5]=true;
												match2deck2[5]=true;
												match2deck3[5]=true;
												p=4;
											} else
												card6.setImageResource(valueCard(slottedDeck[5]));
										}
									}
									if (activc5==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[4]) {
												card5.setImageResource(valueCard(slottedDeck[4]+52));
										        	myObjectRotate5.setFillAfter(true);
										        	myObjectRotate5.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card5.startAnimation(myObjectRotate5); mysoundfx(4);												
												
												latag[4]=true;
												match2deck1[4]=true;
												match2deck2[4]=true;
												match2deck3[4]=true;
												p=4;
											} else
												card5.setImageResource(valueCard(slottedDeck[4]));
										}
									}
									if (activc4==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[3]) {
												card4.setImageResource(valueCard(slottedDeck[3]+52));
										        	myObjectRotate4.setFillAfter(true);
										        	myObjectRotate4.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card4.startAnimation(myObjectRotate4); mysoundfx(4);													
																							
												latag[3]=true;
												match2deck1[3]=true;
												match2deck2[3]=true;
												match2deck3[3]=true;
												p=4;
											} else
												card4.setImageResource(valueCard(slottedDeck[3]));
										}
									}
									//6th layer
									
									if (activc2==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[1]) {
												card2.setImageResource(valueCard(slottedDeck[1]+52));
										        	myObjectRotate2.setFillAfter(true);
										        	myObjectRotate2.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card2.startAnimation(myObjectRotate2); mysoundfx(4);												
												
												latag[1]=true;
												match2deck1[1]=true;
												match2deck2[1]=true;
												match2deck3[1]=true;
												p=4;
											} else
												card2.setImageResource(valueCard(slottedDeck[1]));
										}
									}
									if (activc3==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[2]) {
												card3.setImageResource(valueCard(slottedDeck[2]+52));
										        	myObjectRotate3.setFillAfter(true);
										        	myObjectRotate3.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card3.startAnimation(myObjectRotate3); mysoundfx(4);												
												
												latag[2]=true;
												match2deck1[2]=true;
												match2deck2[2]=true;
												match2deck3[2]=true;
												p=4;
											} else
												card3.setImageResource(valueCard(slottedDeck[2]));
										}
									}
									//7th layer
									if (activc1==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[0]) {
												card1.setImageResource(valueCard(slottedDeck[0]+52));
										        	myObjectRotate1.setFillAfter(true);
										        	myObjectRotate1.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
										        	});
									        		card1.startAnimation(myObjectRotate1); mysoundfx(4);												
												
												latag[0]=true;
												match2deck1[0]=true;
												match2deck2[0]=true;
												match2deck3[0]=true;
												p=4;
											} else
												card1.setImageResource(valueCard(slottedDeck[0]));
										}
									}
								
								}
						/*	} else 
								dealDeck1.setImageResource(valueCard(slottedDeck[next2jkatch]));*/
						}
					});

					dealDeck2.setOnClickListener(new View.OnClickListener() {
					
						public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
							int next2gkatch=jkatch-1;
								int ht=0;
								for (ht=0;ht<52;ht++) {
									clickna[ht]=false;
								}
								goldkeyOn=false;
								if (gipit==true)  {
									int p=0,mkatch=0;
									int[] matchbox = new int[4];
									mkatch=slottedDeck[next2gkatch];
									matchbox=tableCard(mkatch);
									if (mkatch!=104) {
										dealDeck2.setImageResource(valueCard(slottedDeck[next2gkatch]+52));
									}	
									if (activc22==true) {	
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[21]) {
												card22.setImageResource(valueCard(slottedDeck[21]+52));
											    	
											        myObjectRotate22.setFillAfter(true);
											        myObjectRotate22.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card22.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card22.startAnimation(myObjectRotate22); mysoundfx(4);
												
												match2deck2[21]=true;
												match2deck3[21]=true;
												latag[21]=true;
												p=4;
											} else
												card22.setImageResource(valueCard(slottedDeck[21]));
										}
									}
									if (activc23) {
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[22]) {
												card23.setImageResource(valueCard(slottedDeck[22]+52));
											    	
											        myObjectRotate23.setFillAfter(true);
											        myObjectRotate23.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card23.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card23.startAnimation(myObjectRotate23); mysoundfx(4);
												
												latag[22]=true;
												match2deck2[22]=true;
												match2deck3[22]=true;
												p=4;
											} else
												card23.setImageResource(valueCard(slottedDeck[22]));
										}
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[23]) {
											card24.setImageResource(valueCard(slottedDeck[23]+52));
											latag[23]=true;
										    	
										        myObjectRotate24.setFillAfter(true);
										        myObjectRotate24.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
									        		//card24.startAnimation(in);
									        		}
								        		public void onAnimationRepeat(Animation _animation) {}
								        		public void onAnimationStart(Animation _animation) {}
										        });
								        		card24.startAnimation(myObjectRotate24); mysoundfx(4);
											
											match2deck2[23]=true;
											match2deck3[23]=true;
											p=4;	
										} else
											card24.setImageResource(valueCard(slottedDeck[23]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[24]) {
											card25.setImageResource(valueCard(slottedDeck[24]+52));
										    	
										        myObjectRotate25.setFillAfter(true);
										        myObjectRotate25.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
									        		//card25.startAnimation(in);
									        		}
								        		public void onAnimationRepeat(Animation _animation) {}
								        		public void onAnimationStart(Animation _animation) {}
										        });
								        		card25.startAnimation(myObjectRotate25); mysoundfx(4);
											
											latag[24]=true;
											match2deck2[24]=true;
											match2deck3[24]=true;
											p=4;
										} else
											card25.setImageResource(valueCard(slottedDeck[24]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[25]) {
											card26.setImageResource(valueCard(slottedDeck[25]+52));
										    	
										        myObjectRotate26.setFillAfter(true);
										        myObjectRotate26.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
									        		//card26.startAnimation(in);
									        		}
								        		public void onAnimationRepeat(Animation _animation) {}
								        		public void onAnimationStart(Animation _animation) {}
										        });
								        		card26.startAnimation(myObjectRotate26); mysoundfx(4);
											
											latag[25]=true;
											match2deck2[25]=true;
											match2deck3[25]=true;
											p=4;
										} else
											card26.setImageResource(valueCard(slottedDeck[25]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[26]) {
											card27.setImageResource(valueCard(slottedDeck[26]+52));
										    	
										        myObjectRotate27.setFillAfter(true);
										        myObjectRotate27.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
									        		//card27.startAnimation(in);
									        		}
								        		public void onAnimationRepeat(Animation _animation) {}
								        		public void onAnimationStart(Animation _animation) {}
										        });
								        		card27.startAnimation(myObjectRotate27); mysoundfx(4);
											latag[26]=true;
											
											match2deck2[26]=true;
											match2deck3[26]=true;
											p=4;
										} else
											card27.setImageResource(valueCard(slottedDeck[26]));
									}
									for (p=0;p<4;p++)  {
										if (matchbox[p]==slottedDeck[27]) {
											card28.setImageResource(valueCard(slottedDeck[27]+52));
										    	
										        myObjectRotate28.setFillAfter(true);
										        myObjectRotate28.setAnimationListener(new AnimationListener() {
									        	public void onAnimationEnd(Animation _animation) {
									        		//card28.startAnimation(in);
									        		}
								        		public void onAnimationRepeat(Animation _animation) {}
								        		public void onAnimationStart(Animation _animation) {}
										        });
								        		card28.startAnimation(myObjectRotate28); mysoundfx(4);
											
											latag[27]=true;
											match2deck2[27]=true;
											match2deck3[27]=true;
											p=4;
										} else
											card28.setImageResource(valueCard(slottedDeck[27]));
									}
									//2nd layer
									if (activc16==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[15]) {
												card16.setImageResource(valueCard(slottedDeck[15]+52));
								        			myObjectRotate16.setFillAfter(true);
								        			myObjectRotate16.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card16.startAnimation(myObjectRotate16); mysoundfx(4);												
												
												latag[15]=true;
												match2deck2[15]=true;
												match2deck3[15]=true;
												p=4;
											} else
												card16.setImageResource(valueCard(slottedDeck[15]));
										}
									}
									if (activc17==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[16]) {
												card17.setImageResource(valueCard(slottedDeck[16]+52));
								        			myObjectRotate17.setFillAfter(true);
								        			myObjectRotate17.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card17.startAnimation(myObjectRotate17); mysoundfx(4);												
												latag[16]=true;
												
												match2deck2[16]=true;
												match2deck3[16]=true;
												p=4;
											} else
												card17.setImageResource(valueCard(slottedDeck[16]));
										}
									}
									if (activc18==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[17]) {
												card18.setImageResource(valueCard(slottedDeck[17]+52));
								        			myObjectRotate18.setFillAfter(true);
								        			myObjectRotate18.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card18.startAnimation(myObjectRotate18); mysoundfx(4);												
												
												latag[17]=true;
												match2deck2[17]=true;
												match2deck3[17]=true;
												p=4;
											} else
												card18.setImageResource(valueCard(slottedDeck[17]));
										}
									}
									if (activc19==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[18]) {
												card19.setImageResource(valueCard(slottedDeck[18]+52));
								        			myObjectRotate19.setFillAfter(true);
								        			myObjectRotate19.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card19.startAnimation(myObjectRotate19); mysoundfx(4);												
												
												latag[18]=true;
												match2deck2[18]=true;
												match2deck3[18]=true;
												p=4;
											} else
												card19.setImageResource(valueCard(slottedDeck[18]));
										}
									}
									if (activc20==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[19]) {
												card20.setImageResource(valueCard(slottedDeck[19]+52));
								        			myObjectRotate20.setFillAfter(true);
								        			myObjectRotate20.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card20.startAnimation(myObjectRotate20); mysoundfx(4);												
												
												latag[19]=true;
												match2deck2[19]=true;
												match2deck3[19]=true;
												p=4;
											} else
												card20.setImageResource(valueCard(slottedDeck[19]));
										}
									}
									if (activc21==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[20]) {
												card21.setImageResource(valueCard(slottedDeck[20]+52));
								        			myObjectRotate21.setFillAfter(true);
								        			myObjectRotate21.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card21.startAnimation(myObjectRotate21); mysoundfx(4);												
												
												latag[20]=true;
												match2deck2[20]=true;
												match2deck3[20]=true;
												p=4;
											} else
												card21.setImageResource(valueCard(slottedDeck[20]));
										}
									}
									//3rd layer
									if (activc11==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[10]) {
												card11.setImageResource(valueCard(slottedDeck[10]+52));
								        			myObjectRotate11.setFillAfter(true);
								        			myObjectRotate11.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card11.startAnimation(myObjectRotate11); mysoundfx(4);												
												
												latag[10]=true;
												match2deck2[10]=true;
												match2deck3[10]=true;
												p=4;
											} else
												card11.setImageResource(valueCard(slottedDeck[10]));
										}
									}

									if (activc12==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[11]) {
												card12.setImageResource(valueCard(slottedDeck[11]+52));
								        			myObjectRotate12.setFillAfter(true);
								        			myObjectRotate12.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card12.startAnimation(myObjectRotate12); mysoundfx(4);												
												
												latag[11]=true;
												match2deck2[11]=true;
												match2deck3[11]=true;
												p=4;
											} else
												card12.setImageResource(valueCard(slottedDeck[11]));
										}
									}

									if (activc13==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[12]) {
												card13.setImageResource(valueCard(slottedDeck[12]+52));
								        			myObjectRotate13.setFillAfter(true);
								        			myObjectRotate13.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card13.startAnimation(myObjectRotate13); mysoundfx(4);												
												
												latag[12]=true;
												match2deck2[12]=true;
												match2deck3[12]=true;
												p=4;
											} else
												card13.setImageResource(valueCard(slottedDeck[12]));
										}
									}
									if (activc14==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[13]) {
												card14.setImageResource(valueCard(slottedDeck[13]+52));
								        			myObjectRotate14.setFillAfter(true);
								        			myObjectRotate14.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card14.startAnimation(myObjectRotate14); mysoundfx(4);												
												
												latag[13]=true;
												match2deck2[13]=true;
												match2deck3[13]=true;
												p=4;
											} else
												card14.setImageResource(valueCard(slottedDeck[13]));
										}
									}
									if (activc15==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[14]) {
												card15.setImageResource(valueCard(slottedDeck[14]+52));
								        			myObjectRotate15.setFillAfter(true);
								        			myObjectRotate15.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card15.startAnimation(myObjectRotate15); mysoundfx(4);												
												
												latag[14]=true;
												match2deck2[14]=true;
												match2deck3[14]=true;
												p=4;
											} else
												card15.setImageResource(valueCard(slottedDeck[14]));
										}
									}
									//4th layer
									if (activc7==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[6]) {
												card7.setImageResource(valueCard(slottedDeck[6]+52));
								        			myObjectRotate7.setFillAfter(true);
								        			myObjectRotate7.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card7.startAnimation(myObjectRotate7); mysoundfx(4);												
												
												latag[6]=true;
												match2deck2[6]=true;
												match2deck3[6]=true;
												p=4;
											} else
												card7.setImageResource(valueCard(slottedDeck[6]));
										}
									}
									
									if (activc8==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[7]) {
												card8.setImageResource(valueCard(slottedDeck[7]+52));
								        			myObjectRotate8.setFillAfter(true);
								        			myObjectRotate8.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card8.startAnimation(myObjectRotate8); mysoundfx(4);												
												
												latag[7]=true;
												match2deck2[7]=true;
												match2deck3[7]=true;
												p=4;
											} else
												card8.setImageResource(valueCard(slottedDeck[7]));
										}
									}
									if (activc9==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[8]) {
												card9.setImageResource(valueCard(slottedDeck[8]+52));
								        			myObjectRotate9.setFillAfter(true);
								        			myObjectRotate9.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card9.startAnimation(myObjectRotate9); mysoundfx(4);												
												
												latag[8]=true;
												match2deck2[8]=true;
												match2deck3[8]=true;
												p=4;
											} else
												card9.setImageResource(valueCard(slottedDeck[8]));
										}
									}

									if (activc10==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[9]) {
												card10.setImageResource(valueCard(slottedDeck[9]+52));
								        			myObjectRotate10.setFillAfter(true);
								        			myObjectRotate10.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card10.startAnimation(myObjectRotate10); mysoundfx(4);												
												
												latag[9]=true;
												match2deck2[9]=true;
												match2deck3[9]=true;
												p=4;
											} else
												card10.setImageResource(valueCard(slottedDeck[9]));
										}
									}
									// 5th layer
									if (activc6==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[5]) {
												card6.setImageResource(valueCard(slottedDeck[5]+52));
								        			myObjectRotate6.setFillAfter(true);
								        			myObjectRotate6.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card6.startAnimation(myObjectRotate6); mysoundfx(4);												
												
												latag[5]=true;
												match2deck2[5]=true;
												match2deck3[5]=true;
												p=4;
											} else
												card6.setImageResource(valueCard(slottedDeck[5]));
										}
									}
									if (activc5==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[4]) {
												card5.setImageResource(valueCard(slottedDeck[4]+52));
								        			myObjectRotate5.setFillAfter(true);
								        			myObjectRotate5.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card5.startAnimation(myObjectRotate5); mysoundfx(4);												
												
												latag[4]=true;
												match2deck2[4]=true;
												match2deck3[4]=true;
												p=4;
											} else
												card5.setImageResource(valueCard(slottedDeck[4]));
										}
									}
									if (activc4==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[3]) {
												card4.setImageResource(valueCard(slottedDeck[3]+52));
								        			myObjectRotate4.setFillAfter(true);
								        			myObjectRotate4.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card4.startAnimation(myObjectRotate4); mysoundfx(4);												
												
												latag[3]=true;
												match2deck2[3]=true;
												match2deck3[3]=true;
												p=4;
											} else
												card4.setImageResource(valueCard(slottedDeck[3]));
										}
									}
									//6th layer
									
									if (activc2==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[1]) {
												card2.setImageResource(valueCard(slottedDeck[1]+52));
								        			myObjectRotate2.setFillAfter(true);
								        			myObjectRotate2.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card2.startAnimation(myObjectRotate2); mysoundfx(4);												
												
												latag[1]=true;
												match2deck2[1]=true;
												match2deck3[1]=true;
												p=4;
											} else
												card2.setImageResource(valueCard(slottedDeck[1]));
										}
									}
									if (activc3==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[2]) {
												card3.setImageResource(valueCard(slottedDeck[2]+52));
								        			myObjectRotate3.setFillAfter(true);
								        			myObjectRotate3.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card3.startAnimation(myObjectRotate3); mysoundfx(4);												
												
												latag[2]=true;
												match2deck2[2]=true;
												match2deck3[2]=true;
												p=4;
											} else
												card3.setImageResource(valueCard(slottedDeck[2]));
										}
									}
									//7th layer
										if (activc1==true)	{
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[0]) {
												card1.setImageResource(valueCard(slottedDeck[0]+52));
								        			myObjectRotate1.setFillAfter(true);
								        			myObjectRotate1.setAnimationListener(new AnimationListener() {
								        			public void onAnimationEnd(Animation _animation) {}
								        				public void onAnimationRepeat(Animation _animation) {}
								   	     				public void onAnimationStart(Animation _animation) {}
										        	});
							        				card1.startAnimation(myObjectRotate1); mysoundfx(4);												
												
												latag[0]=true;
												match2deck2[0]=true;
												match2deck3[0]=true;
												p=4;
											} else
												card1.setImageResource(valueCard(slottedDeck[0]));
										}
									}

							}
						}
					});
					
				} //else
					//Toast.makeText(v.getContext(), "KALIWA", Toast.LENGTH_LONG).show();
			  }
			}
		}); 		

/*		card.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;

				mykatch=slottedDeck[];
				mykcatch=valueCard(mykatch);
				if (activc==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=1XX) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(1XX);
					card.setImageResource(mykcatch);
					checkDeck1();
					checkDeck2();
					checkAll(,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if ((ocard[]==true)&&(ocard[]==true)) {
					if (activc==false) {
						mysoundfx(3);
						card.setImageResource(mykcatch);
					}
					activc=true;
				}
				
				if (goldkeyOn==true) {
					int it=0,ni=0;
					goldkeyOn=false;					
					openbygoldkey=true;
					for (it=0;it<28;it++) {
						match2deck1[it]=false;
						match2deck2[it]=false;
						match2deck3[it]=false;	
					}
					for (it=0;it<21;it++) {
						for (myp=0;myp<4;myp++)  {
							if (mymatchbox[myp]==slottedDeck[it]) {
								goldvalues[0]=it;
								ni++;
								myp=4;
								it=21;		
							}
						}

					}
					matchcards[0]=mykatch;
					if (goldvalues[0]!=-1) {if (slottedDeck[goldvalues[0]]<104) { openbygold[goldvalues[0]]=true;}}
			 		switch (goldvalues[0]) {
							case(0)	:card1.setImageResource(valueCard(slottedDeck[0]+52));activc1=true;break;
							case(1)	:card2.setImageResource(valueCard(slottedDeck[1]+52));activc2=true;break;
							case(2)	:card3.setImageResource(valueCard(slottedDeck[2]+52));activc3=true;break;
							case(3)	:card4.setImageResource(valueCard(slottedDeck[3]+52));activc4=true;break;
							case(4)	:card5.setImageResource(valueCard(slottedDeck[4]+52));activc5=true;break;
							case(5)	:card6.setImageResource(valueCard(slottedDeck[5]+52));activc6=true;break;
							case(6)	:card7.setImageResource(valueCard(slottedDeck[6]+52));activc7=true;break;
							case(7)	:card8.setImageResource(valueCard(slottedDeck[7]+52));activc8=true;break;
							case(8)	:card9.setImageResource(valueCard(slottedDeck[8]+52));activc9=true;break;
							case(9)	:card10.setImageResource(valueCard(slottedDeck[9]+52));activc10=true;break;
							case(10) :card11.setImageResource(valueCard(slottedDeck[10]+52));activc11=true;break;	
							case(11) :card12.setImageResource(valueCard(slottedDeck[11]+52));activc12=true;break;	
							case(12) :card13.setImageResource(valueCard(slottedDeck[12]+52));activc13=true;break;	
							case(13) :card14.setImageResource(valueCard(slottedDeck[13]+52));activc14=true;break;	
							case(14) :card15.setImageResource(valueCard(slottedDeck[14]+52));activc15=true;break;	
							case(15) :card16.setImageResource(valueCard(slottedDeck[15]+52));activc16=true;break;	
							case(16) :card17.setImageResource(valueCard(slottedDeck[16]+52));activc17=true;break;	
							case(17) :card18.setImageResource(valueCard(slottedDeck[17]+52));activc18=true;break;	
							case(18) :card19.setImageResource(valueCard(slottedDeck[18]+52));activc19=true;break;	
							case(19) :card20.setImageResource(valueCard(slottedDeck[19]+52));activc20=true;break;	
							case(20) :card21.setImageResource(valueCard(slottedDeck[20]+52));activc21=true;break;	
					}
			 		goldvalues[0]=-1;
				}	
				v.refreshDrawableState();
			}
		}); */	
		
		card1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[0];
				mykcatch=valueCard(mykatch);
				if (activc1==true) {
					mykcatch=valueCard(mykatch+52);
					clickna[0]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=114) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(114);
					card1.setImageResource(mykcatch);
					checkDeck1(1);
					checkDeck2(1);
					checkAll(1,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[6]==true) {
					if (activc1==false) {
						mysoundfx(3);
						card1.setImageResource(mykcatch);
					}
					activc1=true;
				}
				checkgoldkey(1,myp,mykatch,mymatchbox);
				v.refreshDrawableState();
			}
		}); 
			
			card2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[1];
				mykcatch=valueCard(mykatch);
				if (activc2==true) {
					mykcatch=valueCard(mykatch+52);
					clickna[1]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=114) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(114);
					card2.setImageResource(mykcatch);
					checkDeck1(2);
					checkDeck2(2);
					checkAll(2,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[7]==true) {
					if (activc2==false) {
						mysoundfx(3);
						card2.setImageResource(mykcatch);
					}
					activc2=true;
				}
				checkgoldkey(2,myp,mykatch,mymatchbox);
				v.refreshDrawableState();
			}
		}); 	
			
			card3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[2];
				mykcatch=valueCard(mykatch);
				if (activc3==true) {
					mykcatch=valueCard(mykatch+52);
					clickna[2]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=114) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(114);
					card3.setImageResource(mykcatch);
					checkDeck1(3);
					checkDeck2(3);
					checkAll(3,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[8]==true) {
					if (activc3==false) {
						mysoundfx(3);
						card3.setImageResource(mykcatch);
					}
					activc3=true;
				}
				checkgoldkey(3,myp,mykatch,mymatchbox);
				v.refreshDrawableState();
			}
		}); 
		
			card4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[3];
				mykcatch=valueCard(mykatch);
				if (activc4==true) {
					mykcatch=valueCard(mykatch+52);
					clickna[3]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=114) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(114);
					card4.setImageResource(mykcatch);
					checkDeck1(4);
					checkDeck2(4);
					checkAll(4,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[9]==true) {
					if (activc4==false) {
						mysoundfx(3);
						card4.setImageResource(mykcatch);
					}
					activc4=true;
				}
				
				checkgoldkey(4,myp,mykatch,mymatchbox);
				v.refreshDrawableState();
			}
		}); 	
			
			card5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[4];
				mykcatch=valueCard(mykatch);
				if (activc5==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[4]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=114) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(114);
					card5.setImageResource(mykcatch);
					checkDeck1(5);
					checkDeck2(5);
					checkAll(5,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[10]==true) {
					if (activc5==false) {
						mysoundfx(3);
						card5.setImageResource(mykcatch);
					}
					activc5=true;
				}
				checkgoldkey(5,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 
			
			card6.setOnClickListener(new View.OnClickListener() {
				
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[5];
				mykcatch=valueCard(mykatch);
				if (activc6==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[5]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=114) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(114);
					card6.setImageResource(mykcatch);
					checkDeck1(6);
					checkDeck2(6);
					checkAll(6,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[11]==true) {
					if (activc6==false) {
						mysoundfx(3);
						card6.setImageResource(mykcatch);
					}
					activc6=true;
				}
				checkgoldkey(6,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 

			
			card7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[6];
				mykcatch=valueCard(mykatch);
				if (activc7==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[6]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=111) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(111);
					card7.setImageResource(mykcatch);
					checkDeck1(7);
					checkDeck2(7);
					checkAll(7,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[12]==true)  {
					if (activc7==false) {
						mysoundfx(3);
						card7.setImageResource(mykcatch);
					}
					activc7=true;
				}
				checkgoldkey(7,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 

			
			card8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[7];
				mykcatch=valueCard(mykatch);
				if (activc8==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[7]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=111) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(111);
					card8.setImageResource(mykcatch);
					checkDeck1(8);
					checkDeck2(8);
					checkAll(8,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[13]==true) {
					if (activc8==false) {
						mysoundfx(3);
						card8.setImageResource(mykcatch);
					}
					activc8=true;
				}
				checkgoldkey(8,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 

			card9.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[8];
				mykcatch=valueCard(mykatch);
				if (activc9==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[8]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=111) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(111);
					card9.setImageResource(mykcatch);
					checkDeck1(9);
					checkDeck2(9);
					checkAll(9,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[14]==true) {
					if (activc9==false) {
						mysoundfx(3);
						card9.setImageResource(mykcatch);
					}
					activc9=true;
				}
				checkgoldkey(9,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 

			card10.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[9];
				mykcatch=valueCard(mykatch);
				if (activc10==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[9]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=111) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(111);
					card10.setImageResource(mykcatch);
					checkDeck1(10);
					checkDeck2(10);
					checkAll(10,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[15]==true) {
					if (activc10==false) {
						mysoundfx(3);
						card10.setImageResource(mykcatch);
					}
					activc10=true;
				}
				checkgoldkey(10,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 
			
			
			card11.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[10];
				mykcatch=valueCard(mykatch);
				if (activc11==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[10]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=111) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(111);
					card11.setImageResource(mykcatch);
					checkDeck1(11);
					checkDeck2(11);
					checkAll(11,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[16]==true) {
					if (activc11==false) {
						mysoundfx(3);
						card11.setImageResource(mykcatch);
					}
					activc11=true;
				}
				checkgoldkey(11,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 

			card12.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[11];
				mykcatch=valueCard(mykatch);
				if (activc12==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[11]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=111) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(111);
					card12.setImageResource(mykcatch);
					checkDeck1(12);
					checkDeck2(12);
					checkAll(12,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[17]==true){
					if (activc12==false) {
						mysoundfx(3);
						card12.setImageResource(mykcatch);
					}
					activc12=true;
				}
				checkgoldkey(12,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 

			card13.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[12];
				mykcatch=valueCard(mykatch);
				if (activc13==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[12]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=109) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(109);
					card13.setImageResource(mykcatch);
					checkDeck1(13);
					checkDeck2(13);
					checkAll(13,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if (ocard[18]==true) {
					if (activc13==false) {
						mysoundfx(3);
						card13.setImageResource(mykcatch);
					}
					activc13=true;
				}
				checkgoldkey(13,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 
			
			card14.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[13];
				mykcatch=valueCard(mykatch);
				if (activc14==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[13]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=109) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(109);
					card14.setImageResource(mykcatch);
					checkDeck1(14);
					checkDeck2(14);
					checkAll(14,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if ((ocard[19]==true)&&(ocard[18]==true)) {
					if (activc14==false) {
						mysoundfx(3);
						card14.setImageResource(mykcatch);
					}
					activc14=true;
				}
				checkgoldkey(14,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 
			
			card15.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int mykatch, mykcatch,myp=0,ap;
				int[] mymatchbox = new int[4];
				boolean mynagmatchna=false;
				for (ap=0;ap<4;ap++) 	{
					mymatchbox[ap]=-1;
				}
				mykatch=slottedDeck[14];
				mykcatch=valueCard(mykatch);
				if (activc15==true) {
					mykcatch=valueCard(mykatch+52);

					clickna[14]=true;
					mymatchbox=tableCard(mykatch);
					if (mykatch!=109) {
						mykcatch=valueCard(mykatch+52);
					} else
						mykcatch=valueCard(109);
					card15.setImageResource(mykcatch);
					checkDeck1(15);
					checkDeck2(15);
					checkAll(15,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
				}	
				if ((ocard[20]==true)&&(ocard[19]==true)) {
					if (activc15==false) {
						mysoundfx(3);
						card15.setImageResource(mykcatch);
					}
					activc15=true;
				}
				checkgoldkey(15,myp,mykatch,mymatchbox);	
				v.refreshDrawableState();
			}
		}); 		 

			card16.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[15];
					mykcatch=valueCard(mykatch);
					if (activc16==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[15]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=109) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(109);
						card16.setImageResource(mykcatch);
						checkDeck1(16);
						checkDeck2(16);
						checkAll(16,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if ((ocard[21]==true)&&(ocard[20]==true)) {
						if (activc16==false) {
							mysoundfx(3);
							card16.setImageResource(mykcatch);
						}
						activc16=true;
					}
					checkgoldkey(16,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 

			card17.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[16];
					mykcatch=valueCard(mykatch);
					if (activc17==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[16]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=109) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(109);
						card17.setImageResource(mykcatch);
						checkDeck1(17);
						checkDeck2(17);
						checkAll(17,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if ((ocard[22]==true)&&(ocard[21]==true)) {
						if (activc17==false) {
							mysoundfx(3);
							card17.setImageResource(mykcatch);
						}
						activc17=true;
					}
					checkgoldkey(17,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		
			
			card18.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[17];
					mykcatch=valueCard(mykatch);
					if (activc18==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[17]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=109) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(109);
						card18.setImageResource(mykcatch);
						checkDeck1(18);
						checkDeck2(18);
						checkAll(18,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if (ocard[22]==true) {
						if (activc18==false) {
							mysoundfx(3);
							card18.setImageResource(mykcatch);
						}
						activc18=true;
					}
					checkgoldkey(18,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			
			card19.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[18];
					mykcatch=valueCard(mykatch);
					if (activc19==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[18]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=107) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(107);
						card19.setImageResource(mykcatch);
						checkDeck1(19);
						checkDeck2(19);
						checkAll(19,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if (ocard[23]==true) {
						if (activc19==false) {
							mysoundfx(3);
							card19.setImageResource(mykcatch);
						}
						activc19=true;
					}
					checkgoldkey(19,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			
			card20.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[19];
					mykcatch=valueCard(mykatch);
					if (activc20==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[19]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=107) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(107);
						card20.setImageResource(mykcatch);
						checkDeck1(20);
						checkDeck2(20);
						checkAll(20,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if (ocard[24]==true) {
						if (activc20==false) {
							mysoundfx(3);
							card20.setImageResource(mykcatch);
						}
						activc20=true;
					}
					checkgoldkey(20,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 

			card21.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[20];
					mykcatch=valueCard(mykatch);
					if (activc21==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[20]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=107) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(107);
						card21.setImageResource(mykcatch);
						checkDeck1(21);
						checkDeck2(21);
						checkAll(21,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if (ocard[25]==true) {
						if (activc21==false) {
							mysoundfx(3);
							card21.setImageResource(mykcatch);
						}
						activc21=true;
					}
					checkgoldkey(21,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			
			card22.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[21];
					mykcatch=valueCard(mykatch);
					if (activc22==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[21]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=107) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(107);
						card22.setImageResource(mykcatch);
						checkDeck1(22);
						checkDeck2(22);
						checkAll(22,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if (ocard[26]==true){
						if (activc22==false) {
							mysoundfx(3);
							card22.setImageResource(mykcatch);
						}
						activc22=true;
					}
					checkgoldkey(22,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			
			card23.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[22];
					mykcatch=valueCard(mykatch);
					if (activc23==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[22]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=107) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(107);
						card23.setImageResource(mykcatch);
						checkDeck1(23);
						checkDeck2(23);
						checkAll(23,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					if (ocard[27]==true) {
						if (activc23==false) {
							mysoundfx(3);
							card23.setImageResource(mykcatch);
						}
						activc23=true;
					}
					checkgoldkey(23,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
		
			card24.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[23];
					mykcatch=valueCard(mykatch);
					if (activc24==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[23]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=104) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(104);
						card24.setImageResource(mykcatch);
						checkDeck1(24);
						checkDeck2(24);
						checkAll(24,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					checkgoldkey(24,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			
			card25.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[24];
					mykcatch=valueCard(mykatch);
					if (activc25==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[24]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=104) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(104);
						card25.setImageResource(mykcatch);
						checkDeck1(25);
						checkDeck2(25);
						checkAll(25,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					checkgoldkey(25,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			
			card26.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[25];
					mykcatch=valueCard(mykatch);
					if (activc26==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[25]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=104) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(104);
						card26.setImageResource(mykcatch);
						checkDeck1(26);
						checkDeck2(26);
						checkAll(26,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					checkgoldkey(26,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			
			
			card27.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[26];
					mykcatch=valueCard(mykatch);
					if (activc27==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[26]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=104) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(104);
						card27.setImageResource(mykcatch);
						checkDeck1(27);
						checkDeck2(27);
						checkAll(27,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					checkgoldkey(27,myp,mykatch,mymatchbox);	
					v.refreshDrawableState();
				}
			}); 		 
			

				card28.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int mykatch, mykcatch,myp=0,ap;
					int[] mymatchbox = new int[4];
					boolean mynagmatchna=false;
					for (ap=0;ap<4;ap++) 	{
						mymatchbox[ap]=-1;
					}
					mykatch=slottedDeck[27];
					mykcatch=valueCard(mykatch);
					if (activc28==true) {
						mykcatch=valueCard(mykatch+52);

						clickna[27]=true;
						mymatchbox=tableCard(mykatch);
						if (mykatch!=104) {
							mykcatch=valueCard(mykatch+52);
						} else
							mykcatch=valueCard(104);
						card28.setImageResource(mykcatch);
						checkDeck1(28);
						checkDeck2(28);
						checkAll(28,mykatch,mykcatch,myp,mymatchbox ,mynagmatchna);
					}	
					checkgoldkey(28,myp,mykatch,mymatchbox);
					v.refreshDrawableState();
				}
			}); 		 



		
		// remaining cards to be deal
		card29.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int cardRemain, ncatch1,ncatch2,ncatch3,r;
				int [] newDeck= new int[52];
				resetCardStatus();
				greenkey.setImageResource(R.drawable.greenkey);
				goldkey.setImageResource(R.drawable.goldkey);
				goldkeyOn=false;
				openbygoldkey=false;
				facedup=true;
				gipit=false;
				int ij=0;
				mysoundfx(1);
				ij=0;
				int aj,sm;
				ij=0;
				aj=0;
				sm=-1;
				for (aj=0;aj<23;aj++) {
					if (openbygold[aj]==true) {
						sm=aj;
						if (slottedDeck[sm]<104) {
							switch (sm) {
							case (0):card1.setImageResource(R.drawable.cover5);activc1=false;break;
							case (1):card2.setImageResource(R.drawable.cover5);activc2=false;break;
							case (2):card3.setImageResource(R.drawable.cover5);activc3=false;break;
							case (3):card4.setImageResource(R.drawable.cover5);activc4=false;break;
							case (4):card5.setImageResource(R.drawable.cover5);activc5=false;break;
							case (5):card6.setImageResource(R.drawable.cover5);activc6=false;break;
							case (6):card7.setImageResource(R.drawable.cover5);activc7=false;break;
							case (7):card8.setImageResource(R.drawable.cover5);activc8=false;break;
							case (8):card9.setImageResource(R.drawable.cover5);activc9=false;break;
							case (9):card10.setImageResource(R.drawable.cover5);activc10=false;break;
							case (10):card11.setImageResource(R.drawable.cover5);activc11=false;break;
							case (11):card12.setImageResource(R.drawable.cover5);activc12=false;break;
							case (12):card13.setImageResource(R.drawable.cover5);activc13=false;break;
							case (13):card14.setImageResource(R.drawable.cover5);activc14=false;break;
							case (14):card15.setImageResource(R.drawable.cover5);activc15=false;break;
							case (15):card16.setImageResource(R.drawable.cover5);activc16=false;break;
							case (16):card17.setImageResource(R.drawable.cover5);activc17=false;break;
							case (17):card18.setImageResource(R.drawable.cover5);activc18=false;break;
							case (18):card19.setImageResource(R.drawable.cover5);activc19=false;break;
							case (19):card20.setImageResource(R.drawable.cover5);activc20=false;break;
							case (20):card21.setImageResource(R.drawable.cover5);activc21=false;break;
							case (21):card22.setImageResource(R.drawable.cover5);activc22=false;break;
							case (22):card23.setImageResource(R.drawable.cover5);activc23=false;break;					
							}
						}
						openbygold[aj]=false;
						aj=23;
					}
				}
				for (ij=0;ij<28;ij++) {
					match2deck1[ij]=false;
					match2deck2[ij]=false;
					match2deck3[ij]=false;
				}
				
				checkside=false;
				checkside2=false;
				cardRemain=allcards-counterCard;

				if (cardRemain==0) {
					dealArea.removeView(dealDeck1);
					dealArea.removeView(dealDeck2);
					dealArea.removeView(dealDeck3);
					card29.setImageResource(R.drawable.dealcard);
					newDeck=renewdDeck(slottedDeck);
					for (r=0;r<52;r++) {
						slottedDeck[r]=newDeck[r];
					}
					//matchdeck1=false;	
					//matchdeck2=false;
					facedup=false;
					nakadeal=false;
					presentCard=28;
					counterCard=28;
					allcards=allcards-removedCard;
					lastdeck=false;
					removedCard=0;
				}
					
				if (cardRemain>0)  {
					ncatch1=valueCard(slottedDeck[presentCard]);
					ncatch2=valueCard(slottedDeck[presentCard+1]);
					ncatch3=valueCard(slottedDeck[presentCard+2]);
					dealDeck1.setImageResource(ncatch1);
					dealDeck1.setPadding(0, 70, 0, 0);
					dealDeck2.setImageResource(ncatch2);
					dealDeck2.setPadding(0, 70, 0, 0);
					dealDeck3.setImageResource(ncatch3);
					dealDeck3.setPadding(0, 70, 0, 0);
					if (nakadeal==false) {
						dealArea.addView(dealDeck1);
						dealArea.addView(dealDeck2);
						dealArea.addView(dealDeck3);
						nakadeal=true;
					}
					counterCard=presentCard;
					presentCard=presentCard+3;
					if (cardRemain>=3) { 
						counterCard=counterCard+3;
						if (cardRemain==3) {
							lastdeck=true;
							card29.setImageResource(R.drawable.last);
							
						}
						dealDeck3.setOnClickListener(new View.OnClickListener() {

							
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								int katch, kcatch,p=0;
								int[] matchbox = new int[4];
											int ht=0;
											for (ht=0;ht<52;ht++) {
												clickna[ht]=false;
											}
								katch=slottedDeck[presentCard-1];
								gkatch=presentCard-1;
								if (katch!=116) {
									matchbox=tableCard(katch);
									kcatch=valueCard(katch+52);
									dealDeck3.setImageResource(kcatch);
									if (activc22==true) {
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[21]) {
												card22.setImageResource(valueCard(slottedDeck[21]+52));
											    	
											        myObjectRotate22.setFillAfter(true);
											        myObjectRotate22.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card22.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card22.startAnimation(myObjectRotate22); mysoundfx(4);
												match2deck3[21]=true;
												latag[21]=true;
												p=4;
											} else
												card22.setImageResource(valueCard(slottedDeck[21]));
										}
									}
									if (activc23==true) {
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[22]) {
												card23.setImageResource(valueCard(slottedDeck[22]+52));
											    	
											        myObjectRotate23.setFillAfter(true);
											        myObjectRotate23.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card23.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card23.startAnimation(myObjectRotate23); mysoundfx(4);
												
												latag[22]=true;
												match2deck3[22]=true;
												p=4;
											} else
												card23.setImageResource(valueCard(slottedDeck[22]));
										}
									}	
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[23]) {
												card24.setImageResource(valueCard(slottedDeck[23]+52));
												latag[23]=true;
											    	
											        myObjectRotate24.setFillAfter(true);
											        myObjectRotate24.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card24.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card24.startAnimation(myObjectRotate24); mysoundfx(4);
												match2deck3[23]=true;
												p=4;	
											} else
												card24.setImageResource(valueCard(slottedDeck[23]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[24]) {
												card25.setImageResource(valueCard(slottedDeck[24]+52));
											    	
											        myObjectRotate25.setFillAfter(true);
											        myObjectRotate25.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card25.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card25.startAnimation(myObjectRotate25); mysoundfx(4);
												
												latag[24]=true;
												match2deck3[24]=true;
												p=4;
											} else
												card25.setImageResource(valueCard(slottedDeck[24]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[25]) {
												card26.setImageResource(valueCard(slottedDeck[25]+52));
											    	
											        myObjectRotate26.setFillAfter(true);
											        myObjectRotate26.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card26.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card26.startAnimation(myObjectRotate26); mysoundfx(4);
												
												latag[25]=true;
												match2deck3[25]=true;
												p=4;
											} else
												card26.setImageResource(valueCard(slottedDeck[25]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[26]) {
												card27.setImageResource(valueCard(slottedDeck[26]+52));
											    	
											        myObjectRotate27.setFillAfter(true);
											        myObjectRotate27.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card27.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card27.startAnimation(myObjectRotate27); mysoundfx(4);
												latag[26]=true;
												match2deck3[26]=true;
												p=4;
											} else
												card27.setImageResource(valueCard(slottedDeck[26]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[27]) {
												card28.setImageResource(valueCard(slottedDeck[27]+52));
											    	
											        myObjectRotate28.setFillAfter(true);
											        myObjectRotate28.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card28.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card28.startAnimation(myObjectRotate28); mysoundfx(4);
												
												latag[27]=true;
												match2deck3[27]=true;
												p=4;
											} else
												card28.setImageResource(valueCard(slottedDeck[27]));
										}
										//2nd layer
										if (activc16==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[15]) {
													card16.setImageResource(valueCard(slottedDeck[15]+52));
									        			myObjectRotate16.setFillAfter(true);
									        			myObjectRotate16.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card16.startAnimation(myObjectRotate16); mysoundfx(4);												
													
													latag[15]=true;
													match2deck3[15]=true;
													p=4;
												} else
													card16.setImageResource(valueCard(slottedDeck[15]));
											}
										}
										if (activc17==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[16]) {
													card17.setImageResource(valueCard(slottedDeck[16]+52));
									        			myObjectRotate17.setFillAfter(true);
									        			myObjectRotate17.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card17.startAnimation(myObjectRotate17); mysoundfx(4);												
													latag[16]=true;
													match2deck3[16]=true;
													p=4;
												} else
													card17.setImageResource(valueCard(slottedDeck[16]));
											}
										}
										if (activc18==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[17]) {
													card18.setImageResource(valueCard(slottedDeck[17]+52));
									        			myObjectRotate18.setFillAfter(true);
									        			myObjectRotate18.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card18.startAnimation(myObjectRotate18); mysoundfx(4);												
													
													latag[17]=true;
													match2deck3[17]=true;
													p=4;
												} else
													card18.setImageResource(valueCard(slottedDeck[17]));
											}
										}
										if (activc19==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[18]) {
													card19.setImageResource(valueCard(slottedDeck[18]+52));
									        			myObjectRotate19.setFillAfter(true);
									        			myObjectRotate19.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card19.startAnimation(myObjectRotate19); mysoundfx(4);												
													
													latag[18]=true;
													match2deck3[18]=true;
													p=4;
												} else
													card19.setImageResource(valueCard(slottedDeck[18]));
											}
										}
										if (activc20==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[19]) {
													card20.setImageResource(valueCard(slottedDeck[19]+52));
									        			myObjectRotate20.setFillAfter(true);
									        			myObjectRotate20.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card20.startAnimation(myObjectRotate20); mysoundfx(4);												
													
													latag[19]=true;
													match2deck3[19]=true;
													p=4;
												} else
													card20.setImageResource(valueCard(slottedDeck[19]));
											}
										}
										if (activc21==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[20]) {
													card21.setImageResource(valueCard(slottedDeck[20]+52));
									        			myObjectRotate21.setFillAfter(true);
									        			myObjectRotate21.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card21.startAnimation(myObjectRotate21); mysoundfx(4);												
													
													latag[20]=true;
													match2deck3[20]=true;
													p=4;
												} else
													card21.setImageResource(valueCard(slottedDeck[20]));
											}
										}
										//3rd layer
										if (activc11==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[10]) {
													card11.setImageResource(valueCard(slottedDeck[10]+52));
									        			myObjectRotate11.setFillAfter(true);
									        			myObjectRotate11.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card11.startAnimation(myObjectRotate11); mysoundfx(4);												
													
													latag[10]=true;
													match2deck3[10]=true;
													p=4;
												} else
													card11.setImageResource(valueCard(slottedDeck[10]));
											}
										}

										if (activc12==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[11]) {
													card12.setImageResource(valueCard(slottedDeck[11]+52));
									        			myObjectRotate12.setFillAfter(true);
									        			myObjectRotate12.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card12.startAnimation(myObjectRotate12); mysoundfx(4);												
													
													latag[11]=true;
													match2deck3[11]=true;
													p=4;
												} else
													card12.setImageResource(valueCard(slottedDeck[11]));
											}
										}

										if (activc13==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[12]) {
													card13.setImageResource(valueCard(slottedDeck[12]+52));
									        			myObjectRotate13.setFillAfter(true);
									        			myObjectRotate13.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card13.startAnimation(myObjectRotate13); mysoundfx(4);												
													
													latag[12]=true;
													match2deck3[12]=true;
													p=4;
												} else
													card13.setImageResource(valueCard(slottedDeck[12]));
											}
										}
										if (activc14==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[13]) {
													card14.setImageResource(valueCard(slottedDeck[13]+52));
									        			myObjectRotate14.setFillAfter(true);
									        			myObjectRotate14.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card14.startAnimation(myObjectRotate14); mysoundfx(4);												
													
													latag[13]=true;
													match2deck3[13]=true;
													p=4;
												} else
													card14.setImageResource(valueCard(slottedDeck[13]));
											}
										}
										if (activc15==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[14]) {
													card15.setImageResource(valueCard(slottedDeck[14]+52));
									        			myObjectRotate15.setFillAfter(true);
									        			myObjectRotate15.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card15.startAnimation(myObjectRotate15); mysoundfx(4);												
													
													latag[14]=true;
													match2deck3[14]=true;
													p=4;
												} else
													card15.setImageResource(valueCard(slottedDeck[14]));
											}
										}
										//4th layer
										if (activc7==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[6]) {
													card7.setImageResource(valueCard(slottedDeck[6]+52));
									        			myObjectRotate7.setFillAfter(true);
									        			myObjectRotate7.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card7.startAnimation(myObjectRotate7); mysoundfx(4);												
													
													latag[6]=true;
													match2deck3[6]=true;
													p=4;
												} else
													card7.setImageResource(valueCard(slottedDeck[6]));
											}
										}
										
										if (activc8==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[7]) {
													card8.setImageResource(valueCard(slottedDeck[7]+52));
									        			myObjectRotate8.setFillAfter(true);
									        			myObjectRotate8.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card8.startAnimation(myObjectRotate8); mysoundfx(4);												
													
													latag[7]=true;
													match2deck3[7]=true;
													p=4;
												} else
													card8.setImageResource(valueCard(slottedDeck[7]));
											}
										}
										if (activc9==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[8]) {
													card9.setImageResource(valueCard(slottedDeck[8]+52));
									        			myObjectRotate9.setFillAfter(true);
									        			myObjectRotate9.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card9.startAnimation(myObjectRotate9); mysoundfx(4);												
													
													latag[8]=true;
													match2deck3[8]=true;
													p=4;
												} else
													card9.setImageResource(valueCard(slottedDeck[8]));
											}
										}

										if (activc10==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[9]) {
													card10.setImageResource(valueCard(slottedDeck[9]+52));
									        			myObjectRotate10.setFillAfter(true);
									        			myObjectRotate10.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card10.startAnimation(myObjectRotate10); mysoundfx(4);												
													
													latag[9]=true;
													match2deck3[9]=true;
													p=4;
												} else
													card10.setImageResource(valueCard(slottedDeck[9]));
											}
										}
										// 5th layer
										if (activc6==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[5]) {
													card6.setImageResource(valueCard(slottedDeck[5]+52));
									        			myObjectRotate6.setFillAfter(true);
									        			myObjectRotate6.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card6.startAnimation(myObjectRotate6); mysoundfx(4);												
													
													latag[5]=true;
													match2deck3[5]=true;
													p=4;
												} else
													card6.setImageResource(valueCard(slottedDeck[5]));
											}
										}
										if (activc5==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[4]) {
													card5.setImageResource(valueCard(slottedDeck[4]+52));
									        			myObjectRotate5.setFillAfter(true);
									        			myObjectRotate5.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card5.startAnimation(myObjectRotate5); mysoundfx(4);												
													
													latag[4]=true;
													match2deck3[4]=true;
													p=4;
												} else
													card5.setImageResource(valueCard(slottedDeck[4]));
											}
										}
										if (activc4==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[3]) {
													card4.setImageResource(valueCard(slottedDeck[3]+52));
									        			myObjectRotate4.setFillAfter(true);
									        			myObjectRotate4.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card4.startAnimation(myObjectRotate4); mysoundfx(4);												
													
													latag[3]=true;
													match2deck3[3]=true;
													p=4;
												} else
													card4.setImageResource(valueCard(slottedDeck[3]));
											}
										}
										//6th layer
										
										if (activc2==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[1]) {
													card2.setImageResource(valueCard(slottedDeck[1]+52));
									        			myObjectRotate2.setFillAfter(true);
									        			myObjectRotate2.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card2.startAnimation(myObjectRotate2); mysoundfx(4);												
													
													latag[1]=true;
													match2deck3[1]=true;
													p=4;
												} else
													card2.setImageResource(valueCard(slottedDeck[1]));
											}
										}
										if (activc3==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[2]) {
													card3.setImageResource(valueCard(slottedDeck[2]+52));
									        			myObjectRotate3.setFillAfter(true);
									        			myObjectRotate3.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card3.startAnimation(myObjectRotate3); mysoundfx(4);												
													
													latag[2]=true;
													match2deck3[2]=true;
													p=4;
												} else
													card3.setImageResource(valueCard(slottedDeck[2]));
											}
										}
										//7th layer
											if (activc1==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[0]) {
													card1.setImageResource(valueCard(slottedDeck[0]+52));
									        			myObjectRotate1.setFillAfter(true);
									        			myObjectRotate1.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card1.startAnimation(myObjectRotate1); mysoundfx(4);												
													
													latag[0]=true;
													match2deck3[0]=true;
													p=4;
												} else
													card1.setImageResource(valueCard(slottedDeck[0]));
											}
										}
								}
							}
					
						});

						
					}
					if (cardRemain==2) { 
						counterCard=counterCard+2;
						card29.setImageResource(R.drawable.last);
						if (slottedDeck[presentCard-1]==-1) {
							checkside2=true;
							dealDeck2.setOnClickListener(new View.OnClickListener() {
								
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									int[] matchbox = new int[4];
									int p=0,mkatch=0;
											int ht=0;
											for (ht=0;ht<52;ht++) {
												clickna[ht]=false;
											}
									if (checkside2==true) {	
										dealDeck2.setImageResource(valueCard(slottedDeck[presentCard-2]+52));
										checkside2=false;
										mkatch=slottedDeck[presentCard-2];
										matchbox=tableCard(mkatch);
										if (activc22==true) { 
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[21]) {
												card22.setImageResource(valueCard(slottedDeck[21]+52));
											    	
											        myObjectRotate22.setFillAfter(true);
											        myObjectRotate22.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card22.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card22.startAnimation(myObjectRotate22); mysoundfx(4);
												
												match2deck2[21]=true;
												match2deck3[21]=true;
												latag[21]=true;
												p=4;
											} else
												card22.setImageResource(valueCard(slottedDeck[21]));
										}
										}
										if (activc23==true) {
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[22]) {
													card23.setImageResource(valueCard(slottedDeck[22]+52));
												    	
												        myObjectRotate23.setFillAfter(true);
												        myObjectRotate23.setAnimationListener(new AnimationListener() {
											        	public void onAnimationEnd(Animation _animation) {
											        		//card23.startAnimation(in);
											        		}
										        		public void onAnimationRepeat(Animation _animation) {}
										        		public void onAnimationStart(Animation _animation) {}
												        });
										        		card23.startAnimation(myObjectRotate23); mysoundfx(4);
													
													latag[22]=true;
													match2deck2[22]=true;
													match2deck3[22]=true;
													p=4;
												} else
													card23.setImageResource(valueCard(slottedDeck[22]));
											}
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[23]) {
												card24.setImageResource(valueCard(slottedDeck[23]+52));
												latag[23]=true;
											    	
											        myObjectRotate24.setFillAfter(true);
											        myObjectRotate24.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card24.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card24.startAnimation(myObjectRotate24); mysoundfx(4);
												
												match2deck2[23]=true;
												match2deck3[23]=true;
												p=4;	
											} else
												card24.setImageResource(valueCard(slottedDeck[23]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[24]) {
												card25.setImageResource(valueCard(slottedDeck[24]+52));
											    	
											        myObjectRotate25.setFillAfter(true);
											        myObjectRotate25.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card25.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card25.startAnimation(myObjectRotate25); mysoundfx(4);
												
												latag[24]=true;
												match2deck2[24]=true;
												match2deck3[24]=true;
												p=4;
											} else
												card25.setImageResource(valueCard(slottedDeck[24]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[25]) {
												card26.setImageResource(valueCard(slottedDeck[25]+52));
											    	
											        myObjectRotate26.setFillAfter(true);
											        myObjectRotate26.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card26.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card26.startAnimation(myObjectRotate26); mysoundfx(4);
												
												latag[25]=true;
												match2deck2[25]=true;
												match2deck3[25]=true;
												p=4;
											} else
												card26.setImageResource(valueCard(slottedDeck[25]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[26]) {
												card27.setImageResource(valueCard(slottedDeck[26]+52));
											    	
											        myObjectRotate27.setFillAfter(true);
											        myObjectRotate27.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card27.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card27.startAnimation(myObjectRotate27); mysoundfx(4);
												latag[26]=true;
												
												match2deck2[26]=true;
												match2deck3[26]=true;
												p=4;
											} else
												card27.setImageResource(valueCard(slottedDeck[26]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[27]) {
												card28.setImageResource(valueCard(slottedDeck[27]+52));
											    	
											        myObjectRotate28.setFillAfter(true);
											        myObjectRotate28.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
										        		//card28.startAnimation(in);
										        		}
									        		public void onAnimationRepeat(Animation _animation) {}
									        		public void onAnimationStart(Animation _animation) {}
											        });
									        		card28.startAnimation(myObjectRotate28); mysoundfx(4);
												
												latag[27]=true;
												match2deck2[27]=true;
												match2deck3[27]=true;
												p=4;
											} else
												card28.setImageResource(valueCard(slottedDeck[27]));
										}
										//2nd layer
										if (activc16==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[15]) {
													card16.setImageResource(valueCard(slottedDeck[15]+52));
									        			myObjectRotate16.setFillAfter(true);
									        			myObjectRotate16.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card16.startAnimation(myObjectRotate16); mysoundfx(4);												
													
													latag[15]=true;
													match2deck2[15]=true;
													match2deck3[15]=true;
													p=4;
												} else
													card16.setImageResource(valueCard(slottedDeck[15]));
											}
										}
										if (activc17==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[16]) {
													card17.setImageResource(valueCard(slottedDeck[16]+52));
									        			myObjectRotate17.setFillAfter(true);
									        			myObjectRotate17.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card17.startAnimation(myObjectRotate17); mysoundfx(4);												
													latag[16]=true;
													
													match2deck2[16]=true;
													match2deck3[16]=true;
													p=4;
												} else
													card17.setImageResource(valueCard(slottedDeck[16]));
											}
										}
										if (activc18==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[17]) {
													card18.setImageResource(valueCard(slottedDeck[17]+52));
									        			myObjectRotate18.setFillAfter(true);
									        			myObjectRotate18.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card18.startAnimation(myObjectRotate18); mysoundfx(4);												
													
													latag[17]=true;
													match2deck2[17]=true;
													match2deck3[17]=true;
													p=4;
												} else
													card18.setImageResource(valueCard(slottedDeck[17]));
											}
										}
										if (activc19==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[18]) {
													card19.setImageResource(valueCard(slottedDeck[18]+52));
									        			myObjectRotate19.setFillAfter(true);
									        			myObjectRotate19.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card19.startAnimation(myObjectRotate19); mysoundfx(4);												
													
													latag[18]=true;
													match2deck2[18]=true;
													match2deck3[18]=true;
													p=4;
												} else
													card19.setImageResource(valueCard(slottedDeck[18]));
											}
										}
										if (activc20==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[19]) {
													card20.setImageResource(valueCard(slottedDeck[19]+52));
									        			myObjectRotate20.setFillAfter(true);
									        			myObjectRotate20.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card20.startAnimation(myObjectRotate20); mysoundfx(4);												
													
													latag[19]=true;
													match2deck2[19]=true;
													match2deck3[19]=true;
													p=4;
												} else
													card20.setImageResource(valueCard(slottedDeck[19]));
											}
										}
										if (activc21==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[20]) {
													card21.setImageResource(valueCard(slottedDeck[20]+52));
									        			myObjectRotate21.setFillAfter(true);
									        			myObjectRotate21.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card21.startAnimation(myObjectRotate21); mysoundfx(4);												
													
													latag[20]=true;
													match2deck2[20]=true;
													match2deck3[20]=true;
													p=4;
												} else
													card21.setImageResource(valueCard(slottedDeck[20]));
											}
										}
										//3rd layer
										if (activc11==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[10]) {
													card11.setImageResource(valueCard(slottedDeck[10]+52));
									        			myObjectRotate11.setFillAfter(true);
									        			myObjectRotate11.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card11.startAnimation(myObjectRotate11); mysoundfx(4);												
													
													latag[10]=true;
													match2deck2[10]=true;
													match2deck3[10]=true;
													p=4;
												} else
													card11.setImageResource(valueCard(slottedDeck[10]));
											}
										}

										if (activc12==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[11]) {
													card12.setImageResource(valueCard(slottedDeck[11]+52));
									        			myObjectRotate12.setFillAfter(true);
									        			myObjectRotate12.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card12.startAnimation(myObjectRotate12); mysoundfx(4);												
													
													latag[11]=true;
													match2deck2[11]=true;
													match2deck3[11]=true;
													p=4;
												} else
													card12.setImageResource(valueCard(slottedDeck[11]));
											}
										}

										if (activc13==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[12]) {
													card13.setImageResource(valueCard(slottedDeck[12]+52));
									        			myObjectRotate13.setFillAfter(true);
									        			myObjectRotate13.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card13.startAnimation(myObjectRotate13); mysoundfx(4);												
													
													latag[12]=true;
													match2deck2[12]=true;
													match2deck3[12]=true;
													p=4;
												} else
													card13.setImageResource(valueCard(slottedDeck[12]));
											}
										}
										if (activc14==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[13]) {
													card14.setImageResource(valueCard(slottedDeck[13]+52));
									        			myObjectRotate14.setFillAfter(true);
									        			myObjectRotate14.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card14.startAnimation(myObjectRotate14); mysoundfx(4);												
													
													latag[13]=true;
													match2deck2[13]=true;
													match2deck3[13]=true;
													p=4;
												} else
													card14.setImageResource(valueCard(slottedDeck[13]));
											}
										}
										if (activc15==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[14]) {
													card15.setImageResource(valueCard(slottedDeck[14]+52));
									        			myObjectRotate15.setFillAfter(true);
									        			myObjectRotate15.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card15.startAnimation(myObjectRotate15); mysoundfx(4);												
													
													latag[14]=true;
													match2deck2[14]=true;
													match2deck3[14]=true;
													p=4;
												} else
													card15.setImageResource(valueCard(slottedDeck[14]));
											}
										}
										//4th layer
										if (activc7==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[6]) {
													card7.setImageResource(valueCard(slottedDeck[6]+52));
									        			myObjectRotate7.setFillAfter(true);
									        			myObjectRotate7.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card7.startAnimation(myObjectRotate7); mysoundfx(4);												
													
													latag[6]=true;
													match2deck2[6]=true;
													match2deck3[6]=true;
													p=4;
												} else
													card7.setImageResource(valueCard(slottedDeck[6]));
											}
										}
										
										if (activc8==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[7]) {
													card8.setImageResource(valueCard(slottedDeck[7]+52));
									        			myObjectRotate8.setFillAfter(true);
									        			myObjectRotate8.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card8.startAnimation(myObjectRotate8); mysoundfx(4);												
													
													latag[7]=true;
													match2deck2[7]=true;
													match2deck3[7]=true;
													p=4;
												} else
													card8.setImageResource(valueCard(slottedDeck[7]));
											}
										}
										if (activc9==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[8]) {
													card9.setImageResource(valueCard(slottedDeck[8]+52));
									        			myObjectRotate9.setFillAfter(true);
									        			myObjectRotate9.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card9.startAnimation(myObjectRotate9); mysoundfx(4);												
													
													latag[8]=true;
													match2deck2[8]=true;
													match2deck3[8]=true;
													p=4;
												} else
													card9.setImageResource(valueCard(slottedDeck[8]));
											}
										}

										if (activc10==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[9]) {
													card10.setImageResource(valueCard(slottedDeck[9]+52));
									        			myObjectRotate10.setFillAfter(true);
									        			myObjectRotate10.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card10.startAnimation(myObjectRotate10); mysoundfx(4);												
													
													latag[9]=true;
													match2deck2[9]=true;
													match2deck3[9]=true;
													p=4;
												} else
													card10.setImageResource(valueCard(slottedDeck[9]));
											}
										}
										// 5th layer
										if (activc6==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[5]) {
													card6.setImageResource(valueCard(slottedDeck[5]+52));
									        			myObjectRotate6.setFillAfter(true);
									        			myObjectRotate6.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card6.startAnimation(myObjectRotate6); mysoundfx(4);												
													
													latag[5]=true;
													match2deck2[5]=true;
													match2deck3[5]=true;
													p=4;
												} else
													card6.setImageResource(valueCard(slottedDeck[5]));
											}
										}
										if (activc5==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[4]) {
													card5.setImageResource(valueCard(slottedDeck[4]+52));
									        			myObjectRotate5.setFillAfter(true);
									        			myObjectRotate5.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card5.startAnimation(myObjectRotate5); mysoundfx(4);												
													
													latag[4]=true;
													match2deck2[4]=true;
													match2deck3[4]=true;
													p=4;
												} else
													card5.setImageResource(valueCard(slottedDeck[4]));
											}
										}
										if (activc4==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[3]) {
													card4.setImageResource(valueCard(slottedDeck[3]+52));
									        			myObjectRotate4.setFillAfter(true);
									        			myObjectRotate4.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card4.startAnimation(myObjectRotate4); mysoundfx(4);												
													
													latag[3]=true;
													match2deck2[3]=true;
													match2deck3[3]=true;
													p=4;
												} else
													card4.setImageResource(valueCard(slottedDeck[3]));
											}
										}
										//6th layer
										
										if (activc2==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[1]) {
													card2.setImageResource(valueCard(slottedDeck[1]+52));
									        			myObjectRotate2.setFillAfter(true);
									        			myObjectRotate2.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card2.startAnimation(myObjectRotate2); mysoundfx(4);												
													
													latag[1]=true;
													match2deck2[1]=true;
													match2deck3[1]=true;
													p=4;
												} else
													card2.setImageResource(valueCard(slottedDeck[1]));
											}
										}
										if (activc3==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[2]) {
													card3.setImageResource(valueCard(slottedDeck[2]+52));
									        			myObjectRotate3.setFillAfter(true);
									        			myObjectRotate3.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card3.startAnimation(myObjectRotate3); mysoundfx(4);												
													
													latag[2]=true;
													match2deck2[2]=true;
													match2deck3[2]=true;
													p=4;
												} else
													card3.setImageResource(valueCard(slottedDeck[2]));
											}
										}
										//7th layer
											if (activc1==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[0]) {
													card1.setImageResource(valueCard(slottedDeck[0]+52));
									        			myObjectRotate1.setFillAfter(true);
									        			myObjectRotate1.setAnimationListener(new AnimationListener() {
									        			public void onAnimationEnd(Animation _animation) {}
									        				public void onAnimationRepeat(Animation _animation) {}
									   	     				public void onAnimationStart(Animation _animation) {}
											        	});
								        				card1.startAnimation(myObjectRotate1); mysoundfx(4);												
													
													latag[0]=true;
													match2deck2[0]=true;
													match2deck3[0]=true;
													p=4;
												} else
													card1.setImageResource(valueCard(slottedDeck[0]));
											}
										}
									}	
								}
							});

						}

					}
					if (cardRemain==1) { 
						counterCard=counterCard+1;
						card29.setImageResource(R.drawable.last);
						if (slottedDeck[presentCard-2]==-1) {
							checkside=true;
							dealDeck1.setOnClickListener(new View.OnClickListener() {
								
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									int[] matchbox = new int[4];
									int p=0,mkatch=0;
											int ht=0;
											for (ht=0;ht<52;ht++) {
												clickna[ht]=false;
											}
									if (checkside==true) {	
										dealDeck1.setImageResource(valueCard(slottedDeck[presentCard-3]+52));
										checkside=false;
										mkatch=slottedDeck[presentCard-3];
										matchbox=tableCard(mkatch);
										if (activc22==true) {
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[21]) {
													card22.setImageResource(valueCard(slottedDeck[21]+52));
													myObjectRotate22.setFillAfter(true);
												        myObjectRotate22.setAnimationListener(new AnimationListener() {
												        	public void onAnimationEnd(Animation _animation) {}
												        	public void onAnimationRepeat(Animation _animation) {}
												        	public void onAnimationStart(Animation _animation) {}
												        });
											        	card22.startAnimation(myObjectRotate22); mysoundfx(4);
													latag[21]=true;
													match2deck1[21]=true;
													match2deck2[21]=true;
													match2deck3[21]=true;
													p=4;
												} else
													card22.setImageResource(valueCard(slottedDeck[21]));
											}
										}
										if (activc23==true) {
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[22]) {
													card23.setImageResource(valueCard(slottedDeck[22]+52));
													
													myObjectRotate23.setFillAfter(true);
											        	myObjectRotate23.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
												        	public void onAnimationStart(Animation _animation) {}
												        });
												        card23.startAnimation(myObjectRotate23); mysoundfx(4);
													latag[22]=true;
													match2deck1[22]=true;
													match2deck2[22]=true;
													match2deck3[22]=true;
													p=4;
												} else
													card23.setImageResource(valueCard(slottedDeck[22]));
											}
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[23]) {
												card24.setImageResource(valueCard(slottedDeck[23]+52));
											    	
										      		myObjectRotate24.setFillAfter(true);
										        	myObjectRotate24.setAnimationListener(new AnimationListener() {
										        		public void onAnimationEnd(Animation _animation) {}
											        	public void onAnimationRepeat(Animation _animation) {}
											        	public void onAnimationStart(Animation _animation) {}
										        	});
										        	card24.startAnimation(myObjectRotate24); mysoundfx(4);	
																							
												latag[23]=true;
												match2deck1[23]=true;
												match2deck2[23]=true;
												match2deck3[23]=true;
												p=4;	
											} else
												card24.setImageResource(valueCard(slottedDeck[23]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[24]) {
												card25.setImageResource(valueCard(slottedDeck[24]+52));
											    	
											        myObjectRotate25.setFillAfter(true);
											        myObjectRotate25.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
											        		//card25.startAnimation(in);
											        	}
											        	public void onAnimationRepeat(Animation _animation) {}
											        	public void onAnimationStart(Animation _animation) {}
											        });
											        card25.startAnimation(myObjectRotate25); mysoundfx(4);
												
												latag[24]=true;
												match2deck1[24]=true;
												match2deck2[24]=true;
												match2deck3[24]=true;
												p=4;
											} else
												card25.setImageResource(valueCard(slottedDeck[24]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[25]) {
												card26.setImageResource(valueCard(slottedDeck[25]+52));
											    	
											        myObjectRotate26.setFillAfter(true);
											        myObjectRotate26.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
											        		//card26.startAnimation(in);
											        	}
											        	public void onAnimationRepeat(Animation _animation) {}
											        	public void onAnimationStart(Animation _animation) {}
											        });
											        card26.startAnimation(myObjectRotate26); mysoundfx(4);
												
												latag[25]=true;
												match2deck1[25]=true;
												match2deck2[25]=true;
												match2deck3[25]=true;
												p=4;
											} else
												card26.setImageResource(valueCard(slottedDeck[25]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[26]) {
												card27.setImageResource(valueCard(slottedDeck[26]+52));
											    	
											        myObjectRotate27.setFillAfter(true);
											        myObjectRotate27.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
											        		//card27.startAnimation(in);
											        	}
											        	public void onAnimationRepeat(Animation _animation) {}
											        	public void onAnimationStart(Animation _animation) {}
											        });
											        card27.startAnimation(myObjectRotate27); mysoundfx(4);
												
												latag[26]=true;	
												match2deck1[26]=true;
												match2deck2[26]=true;
												match2deck3[26]=true;
												p=4;
											} else
												card27.setImageResource(valueCard(slottedDeck[26]));
										}
										for (p=0;p<4;p++)  {
											if (matchbox[p]==slottedDeck[27]) {
												card28.setImageResource(valueCard(slottedDeck[27]+52));
											    	
											        myObjectRotate28.setFillAfter(true);
											        myObjectRotate28.setAnimationListener(new AnimationListener() {
										        	public void onAnimationEnd(Animation _animation) {
											        		//card28.startAnimation(in);
											        	}
											        	public void onAnimationRepeat(Animation _animation) {}
											        	public void onAnimationStart(Animation _animation) {}
											        });
											        card28.startAnimation(myObjectRotate28); mysoundfx(4);
												
												latag[27]=true;
												match2deck1[27]=true;
												match2deck2[27]=true;
												match2deck3[27]=true;
												p=4;
											} else
												card28.setImageResource(valueCard(slottedDeck[27]));
										}
										//2nd layer
										if (activc16==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[15]) {
													card16.setImageResource(valueCard(slottedDeck[15]+52));
											        	myObjectRotate16.setFillAfter(true);
											        	myObjectRotate16.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card16.startAnimation(myObjectRotate16); mysoundfx(4);												
													
													latag[15]=true;
													match2deck1[15]=true;
													match2deck2[15]=true;
													match2deck3[15]=true;
													p=4;
												} else
													card16.setImageResource(valueCard(slottedDeck[15]));
											}
										}

										if (activc17==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[16]) {
													card17.setImageResource(valueCard(slottedDeck[16]+52));
											        	myObjectRotate17.setFillAfter(true);
											        	myObjectRotate17.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card17.startAnimation(myObjectRotate17); mysoundfx(4);												
													
													latag[16]=true;
													match2deck1[16]=true;
													match2deck2[16]=true;
													match2deck3[16]=true;
													p=4;
												} else
													card17.setImageResource(valueCard(slottedDeck[16]));
											}
										}

										if (activc18==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[17]) {
													card18.setImageResource(valueCard(slottedDeck[17]+52));
											        	myObjectRotate18.setFillAfter(true);
											        	myObjectRotate18.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card18.startAnimation(myObjectRotate18); mysoundfx(4);												
													
													latag[17]=true;
													match2deck1[17]=true;
													match2deck2[17]=true;
													match2deck3[17]=true;
													p=4;
												} else
													card18.setImageResource(valueCard(slottedDeck[17]));
											}
										}
										if (activc19==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[18]) {
													card19.setImageResource(valueCard(slottedDeck[18]+52));
											        	myObjectRotate19.setFillAfter(true);
											        	myObjectRotate19.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card19.startAnimation(myObjectRotate19); mysoundfx(4);												
													
													latag[18]=true;
													match2deck1[18]=true;
													match2deck2[18]=true;
													match2deck3[18]=true;
													p=4;
												} else
													card19.setImageResource(valueCard(slottedDeck[18]));
											}
										}
										if (activc20==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[19]) {
													card20.setImageResource(valueCard(slottedDeck[19]+52));
											        	myObjectRotate20.setFillAfter(true);
											        	myObjectRotate20.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card20.startAnimation(myObjectRotate20); mysoundfx(4);												
													
													latag[19]=true;
													match2deck1[19]=true;
													match2deck2[19]=true;
													match2deck3[19]=true;
													p=4;
												} else
													card20.setImageResource(valueCard(slottedDeck[19]));
											}
										}
										if (activc21==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[20]) {
													card21.setImageResource(valueCard(slottedDeck[20]+52));
											        	myObjectRotate21.setFillAfter(true);
											        	myObjectRotate21.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card21.startAnimation(myObjectRotate21); mysoundfx(4);												
													
													latag[20]=true;
													match2deck1[20]=true;
													match2deck2[20]=true;
													match2deck3[20]=true;
													p=4;
												} else
													card21.setImageResource(valueCard(slottedDeck[20]));
											}
										}
										//3rd layer
										if (activc11==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[10]) {
													card11.setImageResource(valueCard(slottedDeck[10]+52));
											        	myObjectRotate11.setFillAfter(true);
											        	myObjectRotate11.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card11.startAnimation(myObjectRotate11); mysoundfx(4);												
													
													latag[10]=true;
													match2deck1[10]=true;
													match2deck2[10]=true;
													match2deck3[10]=true;
													p=4;
												} else
													card11.setImageResource(valueCard(slottedDeck[10]));
											}
										}

										if (activc12==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[11]) {
													card12.setImageResource(valueCard(slottedDeck[11]+52));
											        	myObjectRotate12.setFillAfter(true);
											        	myObjectRotate12.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card12.startAnimation(myObjectRotate12); mysoundfx(4);												
													
													latag[11]=true;
													match2deck1[11]=true;
													match2deck2[11]=true;
													match2deck3[11]=true;
													p=4;
												} else
													card12.setImageResource(valueCard(slottedDeck[11]));
											}
										}

										if (activc13==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[12]) {
													card13.setImageResource(valueCard(slottedDeck[12]+52));
											        	myObjectRotate13.setFillAfter(true);
											        	myObjectRotate13.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card13.startAnimation(myObjectRotate13); mysoundfx(4);												
													
													latag[12]=true;
													match2deck1[12]=true;
													match2deck2[12]=true;
													match2deck3[12]=true;
													p=4;
												} else
													card13.setImageResource(valueCard(slottedDeck[12]));
											}
										}
										if (activc14==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[13]) {
													card14.setImageResource(valueCard(slottedDeck[13]+52));
											        	myObjectRotate14.setFillAfter(true);
											        	myObjectRotate14.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card14.startAnimation(myObjectRotate14); mysoundfx(4);												
													
													latag[13]=true;
													match2deck1[13]=true;
													match2deck2[13]=true;
													match2deck3[13]=true;
													p=4;
												} else
													card14.setImageResource(valueCard(slottedDeck[13]));
											}
										}
										if (activc15==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[14]) {
													card15.setImageResource(valueCard(slottedDeck[14]+52));
											        	myObjectRotate15.setFillAfter(true);
											        	myObjectRotate15.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card15.startAnimation(myObjectRotate15); mysoundfx(4);												
													
													latag[14]=true;
													match2deck1[14]=true;
													match2deck2[14]=true;
													match2deck3[14]=true;
													p=4;
												} else
													card15.setImageResource(valueCard(slottedDeck[14]));
											}
										}
										//4th layer
										if (activc7==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[6]) {
													card7.setImageResource(valueCard(slottedDeck[6]+52));
											        	myObjectRotate7.setFillAfter(true);
											        	myObjectRotate7.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card7.startAnimation(myObjectRotate7); mysoundfx(4);												
													
													latag[6]=true;
													match2deck1[6]=true;
													match2deck2[6]=true;
													match2deck3[6]=true;
													p=4;
												} else
													card7.setImageResource(valueCard(slottedDeck[6]));
											}
										}
										
										if (activc8==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[7]) {
													card8.setImageResource(valueCard(slottedDeck[7]+52));
											        	myObjectRotate8.setFillAfter(true);
											        	myObjectRotate8.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card8.startAnimation(myObjectRotate8); mysoundfx(4);												
													
													latag[7]=true;
													match2deck1[7]=true;
													match2deck2[7]=true;
													match2deck3[7]=true;
													p=4;
												} else
													card8.setImageResource(valueCard(slottedDeck[7]));
											}
										}
										if (activc9==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[8]) {
													card9.setImageResource(valueCard(slottedDeck[8]+52));
											        	myObjectRotate9.setFillAfter(true);
											        	myObjectRotate9.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card9.startAnimation(myObjectRotate9); mysoundfx(4);												
													
													latag[8]=true;
													match2deck1[8]=true;
													match2deck2[8]=true;
													match2deck3[8]=true;
													p=4;
												} else
													card9.setImageResource(valueCard(slottedDeck[8]));
											}
										}

										if (activc10==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[9]) {
													card10.setImageResource(valueCard(slottedDeck[9]+52));
											        	myObjectRotate10.setFillAfter(true);
											        	myObjectRotate10.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card10.startAnimation(myObjectRotate10); mysoundfx(4);												
													
													latag[9]=true;
													match2deck1[9]=true;
													match2deck2[9]=true;
													match2deck3[9]=true;
													p=4;
												} else
													card10.setImageResource(valueCard(slottedDeck[9]));
											}
										}
										// 5th layer
										if (activc6==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[5]) {
													card6.setImageResource(valueCard(slottedDeck[5]+52));
											        	myObjectRotate6.setFillAfter(true);
											        	myObjectRotate6.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card6.startAnimation(myObjectRotate6); mysoundfx(4);												
													
													latag[5]=true;
													match2deck1[5]=true;
													match2deck2[5]=true;
													match2deck3[5]=true;
													p=4;
												} else
													card6.setImageResource(valueCard(slottedDeck[5]));
											}
										}
										if (activc5==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[4]) {
													card5.setImageResource(valueCard(slottedDeck[4]+52));
											        	myObjectRotate5.setFillAfter(true);
											        	myObjectRotate5.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card5.startAnimation(myObjectRotate5); mysoundfx(4);												
													
													latag[4]=true;
													match2deck1[4]=true;
													match2deck2[4]=true;
													match2deck3[4]=true;
													p=4;
												} else
													card5.setImageResource(valueCard(slottedDeck[4]));
											}
										}
										if (activc4==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[3]) {
													card4.setImageResource(valueCard(slottedDeck[3]+52));
											        	myObjectRotate4.setFillAfter(true);
											        	myObjectRotate4.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card4.startAnimation(myObjectRotate4); mysoundfx(4);													
																								
													latag[3]=true;
													match2deck1[3]=true;
													match2deck2[3]=true;
													match2deck3[3]=true;
													p=4;
												} else
													card4.setImageResource(valueCard(slottedDeck[3]));
											}
										}
										//6th layer
										
										if (activc2==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[1]) {
													card2.setImageResource(valueCard(slottedDeck[1]+52));
											        	myObjectRotate2.setFillAfter(true);
											        	myObjectRotate2.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card2.startAnimation(myObjectRotate2); mysoundfx(4);												
													
													latag[1]=true;
													match2deck1[1]=true;
													match2deck2[1]=true;
													match2deck3[1]=true;
													p=4;
												} else
													card2.setImageResource(valueCard(slottedDeck[1]));
											}
										}
										if (activc3==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[2]) {
													card3.setImageResource(valueCard(slottedDeck[2]+52));
											        	myObjectRotate3.setFillAfter(true);
											        	myObjectRotate3.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card3.startAnimation(myObjectRotate3); mysoundfx(4);												
													
													latag[2]=true;
													match2deck1[2]=true;
													match2deck2[2]=true;
													match2deck3[2]=true;
													p=4;
												} else
													card3.setImageResource(valueCard(slottedDeck[2]));
											}
										}
										//7th layer
										if (activc1==true)	{
											for (p=0;p<4;p++)  {
												if (matchbox[p]==slottedDeck[0]) {
													card1.setImageResource(valueCard(slottedDeck[0]+52));
											        	myObjectRotate1.setFillAfter(true);
											        	myObjectRotate1.setAnimationListener(new AnimationListener() {
											        		public void onAnimationEnd(Animation _animation) {}
											        		public void onAnimationRepeat(Animation _animation) {}
											        		public void onAnimationStart(Animation _animation) {}
											        	});
										        		card1.startAnimation(myObjectRotate1); mysoundfx(4);												
													
													latag[0]=true;
													match2deck1[0]=true;
													match2deck2[0]=true;
													match2deck3[0]=true;
													p=4;
												} else
													card1.setImageResource(valueCard(slottedDeck[0]));
											}
										}
										
									}	
								}
							});

						}

					}

				}
				v.refreshDrawableState();
				jkatch=presentCard-1;
			}
		});// end of deal click
	}
   /** Called when the activity is first created. */
 }
