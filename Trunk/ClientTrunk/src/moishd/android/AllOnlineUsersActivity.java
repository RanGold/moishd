package moishd.android;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class AllOnlineUsersActivity extends Activity {
//
//    private static class EfficientAdapter extends BaseAdapter {
//        private LayoutInflater mInflater;
//        private Bitmap mIcon1;
//        private Bitmap mIcon2;
//        private Bitmap moishd_logo;
//
//        public EfficientAdapter(Context context) {
//            // Cache the LayoutInflate to avoid asking for a new one each time.
//            mInflater = LayoutInflater.from(context);
//
//            // Icons bound to the rows.
//            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon48x48_1);
//            mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon48x48_2);
//            moishd_logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.moishd_logo);
//        }
//
//        public int getCount() {
//            return DATA.length;
//        }
//
//        public Object getItem(int position) {
//            return position;
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // A ViewHolder keeps references to children views to avoid unneccessary calls
//            // to findViewById() on each row.
//            ViewHolder holder;
//
//            // When convertView is not null, we can reuse it directly, there is no need
//            // to reinflate it. We only inflate a new View when the convertView supplied
//            // by ListView is null.
//            if (convertView == null) {
//                convertView = mInflater.inflate(R.layout.all_users_list_item, null);
//
//                // Creates a ViewHolder and store references to the two children views
//                // we want to bind data to.
//                holder = new ViewHolder();
//                holder.userName = (TextView) convertView.findViewById(R.id.text);
//                holder.userPicture = (ImageView) convertView.findViewById(R.id.userPicture);
//                holder.userRank = (ImageView) convertView.findViewById(R.id.userRank);
//                holder.moishdButton = (ImageButton) convertView.findViewById(R.id.moishdButton);
//                
//                convertView.setTag(holder);
//            } else {
//                // Get the ViewHolder back to get fast access to the TextView
//                // and the ImageView.
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            // Bind the data efficiently with the holder.
//            holder.userName.setText(DATA[position]);
//            holder.userPicture.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
//            holder.userRank.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
//            holder.moishdButton.setBackgroundResource(R.drawable.moishd_logo);
//            holder.moishdButton.setClickable(true);
//
//            return convertView;
//        }
//
//        static class ViewHolder {
//            TextView userName;
//            ImageView userPicture;
//            ImageView userRank;
//            ImageButton moishdButton;
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.all_users_layout);
//        
//        ListView list = (ListView) findViewById(R.id.allUsersListView);
//        list.setOnItemClickListener(new OnItemClickListener() {
//        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        		inviteUserToMoish(view);
//      	}
//        });
//        list.setAdapter(new EfficientAdapter(this));
//
//    }
//    
//    protected void inviteUserToMoish(View view){
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("Moish'd! cannot start as it requires a Google account for registration. " +
//				"Retry?")
//		       .setCancelable(false)
//		       .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		                dialog.cancel();
//		           }
//		       })
//		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		        	   finish();
//		           }
//		       });
//		AlertDialog alert = builder.create();  
//		alert.show();
//    	
//    }
//
//
//    private static final String[] DATA = {
//            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
//            "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis",
//            "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
//            "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
//            "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh",
//            "Anthoriro", "Appenzell", "Aragon", "Ardi Gasna", "Ardrahan",
//            "Armenian String", "Aromes au Gene de Marc", "Asadero", "Asiago",
//            "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss", "Babybel",
//            "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal",
//            "Banon", "Barry's Bay Cheddar", "Basing", "Basket Cheese",
//            "Bath Cheese", "Bavarian Bergkase", "Baylough", "Beaufort",
//            "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
//            "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir",
//            "Bierkase", "Bishop Kennedy", "Blarney", "Bleu d'Auvergne",
//            "Bleu de Gex", "Bleu de Laqueuille", "Bleu de Septmoncel",
//            "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
//            "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini",
//            "Bocconcini (Australian)", "Boeren Leidenkaas", "Bonchester",
//            "Bosworth", "Bougon", "Boule Du Roves", "Boulette d'Avesnes",
//            "Boursault", "Boursin", "Bouyssou", "Bra", "Braudostur",
//            "Breakfast Cheese", "Brebis du Lavort", "Brebis du Lochois",
//            "Brebis du Puyfaucon", "Bresse Bleu", "Brick", "Brie",
//            "Brie de Meaux", "Brie de Melun", "Brillat-Savarin", "Brin",
//            "Brin d' Amour", "Brin d'Amour", "Brinza (Burduf Brinza)",
//            "Briquette de Brebis", "Briquette du Forez", "Broccio",
//            "Broccio Demi-Affine", "Brousse du Rove", "Bruder Basil",
//            "Brusselae Kaas (Fromage de Bruxelles)", "Bryndza",
//            "Buchette d'Anjou", "Buffalo", "Burgos", "Butte", "Butterkase",
//            "Button (Innes)", "Buxton Blue", "Cabecou", "Caboc", "Cabrales",
//            "Cachaille", "Caciocavallo", "Caciotta", "Caerphilly",
//            "Cairnsmore", "Calenzana", "Cambazola", "Camembert de Normandie",
//            "Canadian Cheddar", "Canestrato", "Cantal", "Caprice des Dieux",
//            "Capricorn Goat", "Capriole Banon", "Carre de l'Est",
//            "Casciotta di Urbino", "Cashel Blue", "Castellano", "Castelleno",
//            "Castelmagno", "Castelo Branco", "Castigliano", "Cathelain",
//            "Celtic Promise", "Cendre d'Olivet", "Cerney", "Chabichou",
//            "Chabichou du Poitou", "Chabis de Gatine", "Chaource", "Charolais",
//            "Chaumes", "Cheddar", "Cheddar Clothbound", "Cheshire", "Chevres",
//            "Chevrotin des Aravis", "Chontaleno", "Civray",
//            "Coeur de Camembert au Calvados", "Coeur de Chevre", "Colby",
//            "Cold Pack", "Comte", "Coolea", "Cooleney", "Coquetdale",
//            "Corleggy", "Cornish Pepper", "Cotherstone", "Cotija",
//            "Cottage Cheese", "Cottage Cheese (Australian)", "Cougar Gold",
//            "Coulommiers", "Coverdale", "Crayeux de Roncq", "Cream Cheese",
//            "Cream Havarti", "Crema Agria", "Crema Mexicana", "Creme Fraiche",
//            "Crescenza", "Croghan", "Crottin de Chavignol",
//            "Crottin du Chavignol", "Crowdie", "Crowley", "Cuajada", "Curd",
//            "Cure Nantais", "Curworthy", "Cwmtawe Pecorino",
//            "Cypress Grove Chevre", "Danablu (Danish Blue)", "Danbo",
//            "Danish Fontina", "Daralagjazsky", "Dauphin", "Delice des Fiouves",
//            "Denhany Dorset Drum", "Derby", "Dessertnyj Belyj", "Devon Blue",
//            "Devon Garland", "Dolcelatte", "Doolin", "Doppelrhamstufel",
//            "Dorset Blue Vinney", "Double Gloucester", "Double Worcester",
//            "Dreux a la Feuille", "Dry Jack", "Duddleswell", "Dunbarra",
//            "Dunlop", "Dunsyre Blue", "Duroblando", "Durrus",
//            "Dutch Mimolette (Commissiekaas)", "Edam", "Edelpilz",
//            "Emental Grand Cru", "Emlett", "Emmental", "Epoisses de Bourgogne",
//            "Esbareich", "Esrom", "Etorki", "Evansdale Farmhouse Brie",
//            "Evora De L'Alentejo", "Exmoor Blue", "Explorateur", "Feta",
//            "Feta (Australian)", "Figue", "Filetta", "Fin-de-Siecle",
//            "Finlandia Swiss", "Finn", "Fiore Sardo", "Fleur du Maquis",
//            "Flor de Guia", "Flower Marie", "Folded",
//            "Folded cheese with mint", "Fondant de Brebis", "Fontainebleau",
//            "Fontal", "Fontina Val d'Aosta", "Formaggio di capra", "Fougerus",
//            "Four Herb Gouda", "Fourme d' Ambert", "Fourme de Haute Loire",
//            "Fourme de Montbrison", "Fresh Jack", "Fresh Mozzarella",
//            "Fresh Ricotta", "Fresh Truffles", "Fribourgeois", "Friesekaas",
//            "Friesian", "Friesla", "Frinault", "Fromage a Raclette",
//            "Fromage Corse", "Fromage de Montagne de Savoie", "Fromage Frais",
//            "Fruit Cream Cheese", "Frying Cheese", "Fynbo", "Gabriel",
//            "Galette du Paludier", "Galette Lyonnaise",
//            "Galloway Goat's Milk Gems", "Gammelost", "Gaperon a l'Ail",
//            "Garrotxa", "Gastanberra", "Geitost", "Gippsland Blue", "Gjetost",
//            "Gloucester", "Golden Cross", "Gorgonzola", "Gornyaltajski",
//            "Gospel Green", "Gouda", "Goutu", "Gowrie", "Grabetto", "Graddost",
//            "Grafton Village Cheddar", "Grana", "Grana Padano", "Grand Vatel",
//            "Grataron d' Areches", "Gratte-Paille", "Graviera", "Greuilh",
//            "Greve", "Gris de Lille", "Gruyere", "Gubbeen", "Guerbigny",
//            "Halloumi", "Halloumy (Australian)", "Haloumi-Style Cheese",
//            "Harbourne Blue", "Havarti", "Heidi Gruyere", "Hereford Hop",
//            "Herrgardsost", "Herriot Farmhouse", "Herve", "Hipi Iti",
//            "Hubbardston Blue Cow", "Hushallsost", "Iberico", "Idaho Goatster",
//            "Idiazabal", "Il Boschetto al Tartufo", "Ile d'Yeu",
//            "Isle of Mull", "Jarlsberg", "Jermi Tortes", "Jibneh Arabieh",
//            "Jindi Brie", "Jubilee Blue", "Juustoleipa", "Kadchgall", "Kaseri",
//            "Kashta", "Kefalotyri", "Kenafa", "Kernhem", "Kervella Affine",
//            "Kikorangi", "King Island Cape Wickham Brie", "King River Gold",
//            "Klosterkaese", "Knockalara", "Kugelkase", "L'Aveyronnais",
//            "L'Ecir de l'Aubrac", "La Taupiniere", "La Vache Qui Rit",
//            "Laguiole", "Lairobell", "Lajta", "Lanark Blue", "Lancashire",
//            "Langres", "Lappi", "Laruns", "Lavistown", "Le Brin",
//            "Le Fium Orbo", "Le Lacandou", "Le Roule", "Leafield", "Lebbene",
//            "Leerdammer", "Leicester", "Leyden", "Limburger",
//            "Lincolnshire Poacher", "Lingot Saint Bousquet d'Orb", "Liptauer",
//            "Little Rydings", "Livarot", "Llanboidy", "Llanglofan Farmhouse",
//            "Loch Arthur Farmhouse", "Loddiswell Avondale", "Longhorn",
//            "Lou Palou", "Lou Pevre", "Lyonnais", "Maasdam", "Macconais",
//            "Mahoe Aged Gouda", "Mahon", "Malvern", "Mamirolle", "Manchego",
//            "Manouri", "Manur", "Marble Cheddar", "Marbled Cheeses",
//            "Maredsous", "Margotin", "Maribo", "Maroilles", "Mascares",
//            "Mascarpone", "Mascarpone (Australian)", "Mascarpone Torta",
//            "Matocq", "Maytag Blue", "Meira", "Menallack Farmhouse",
//            "Menonita", "Meredith Blue", "Mesost", "Metton (Cancoillotte)",
//            "Meyer Vintage Gouda", "Mihalic Peynir", "Milleens", "Mimolette",
//            "Mine-Gabhar", "Mini Baby Bells", "Mixte", "Molbo",
//            "Monastery Cheeses", "Mondseer", "Mont D'or Lyonnais", "Montasio",
//            "Monterey Jack", "Monterey Jack Dry", "Morbier",
//            "Morbier Cru de Montagne", "Mothais a la Feuille", "Mozzarella",
//            "Mozzarella (Australian)", "Mozzarella di Bufala",
//            "Mozzarella Fresh, in water", "Mozzarella Rolls", "Munster",
//            "Murol", "Mycella", "Myzithra", "Naboulsi", "Nantais",
//            "Neufchatel", "Neufchatel (Australian)", "Niolo", "Nokkelost",
//            "Northumberland", "Oaxaca", "Olde York", "Olivet au Foin",
//            "Olivet Bleu", "Olivet Cendre", "Orkney Extra Mature Cheddar",
//            "Orla", "Oschtjepka", "Ossau Fermier", "Ossau-Iraty", "Oszczypek",
//            "Oxford Blue", "P'tit Berrichon", "Palet de Babligny", "Paneer",
//            "Panela", "Pannerone", "Pant ys Gawn", "Parmesan (Parmigiano)",
//            "Parmigiano Reggiano", "Pas de l'Escalette", "Passendale",
//            "Pasteurized Processed", "Pate de Fromage", "Patefine Fort",
//            "Pave d'Affinois", "Pave d'Auge", "Pave de Chirac",
//            "Pave du Berry", "Pecorino", "Pecorino in Walnut Leaves",
//            "Pecorino Romano", "Peekskill Pyramid", "Pelardon des Cevennes",
//            "Pelardon des Corbieres", "Penamellera", "Penbryn", "Pencarreg",
//            "Perail de Brebis", "Petit Morin", "Petit Pardou", "Petit-Suisse",
//            "Picodon de Chevre", "Picos de Europa", "Piora",
//            "Pithtviers au Foin", "Plateau de Herve", "Plymouth Cheese",
//            "Podhalanski", "Poivre d'Ane", "Polkolbin", "Pont l'Eveque",
//            "Port Nicholson", "Port-Salut", "Postel", "Pouligny-Saint-Pierre",
//            "Pourly", "Prastost", "Pressato", "Prince-Jean",
//            "Processed Cheddar", "Provolone", "Provolone (Australian)",
//            "Pyengana Cheddar", "Pyramide", "Quark", "Quark (Australian)",
//            "Quartirolo Lombardo", "Quatre-Vents", "Quercy Petit",
//            "Queso Blanco", "Queso Blanco con Frutas --Pina y Mango",
//            "Queso de Murcia", "Queso del Montsec", "Queso del Tietar",
//            "Queso Fresco", "Queso Fresco (Adobera)", "Queso Iberico",
//            "Queso Jalapeno", "Queso Majorero", "Queso Media Luna",
//            "Queso Para Frier", "Queso Quesadilla", "Rabacal", "Raclette",
//            "Ragusano", "Raschera", "Reblochon", "Red Leicester",
//            "Regal de la Dombes", "Reggianito", "Remedou", "Requeson",
//            "Richelieu", "Ricotta", "Ricotta (Australian)", "Ricotta Salata",
//            "Ridder", "Rigotte", "Rocamadour", "Rollot", "Romano",
//            "Romans Part Dieu", "Roncal", "Roquefort", "Roule",
//            "Rouleau De Beaulieu", "Royalp Tilsit", "Rubens", "Rustinu",
//            "Saaland Pfarr", "Saanenkaese", "Saga", "Sage Derby",
//            "Sainte Maure", "Saint-Marcellin", "Saint-Nectaire",
//            "Saint-Paulin", "Salers", "Samso", "San Simon", "Sancerre",
//            "Sap Sago", "Sardo", "Sardo Egyptian", "Sbrinz", "Scamorza",
//            "Schabzieger", "Schloss", "Selles sur Cher", "Selva", "Serat",
//            "Seriously Strong Cheddar", "Serra da Estrela", "Sharpam",
//            "Shelburne Cheddar", "Shropshire Blue", "Siraz", "Sirene",
//            "Smoked Gouda", "Somerset Brie", "Sonoma Jack",
//            "Sottocenare al Tartufo", "Soumaintrain", "Sourire Lozerien",
//            "Spenwood", "Sraffordshire Organic", "St. Agur Blue Cheese",
//            "Stilton", "Stinking Bishop", "String", "Sussex Slipcote",
//            "Sveciaost", "Swaledale", "Sweet Style Swiss", "Swiss",
//            "Syrian (Armenian String)", "Tala", "Taleggio", "Tamie",
//            "Tasmania Highland Chevre Log", "Taupiniere", "Teifi", "Telemea",
//            "Testouri", "Tete de Moine", "Tetilla", "Texas Goat Cheese",
//            "Tibet", "Tillamook Cheddar", "Tilsit", "Timboon Brie", "Toma",
//            "Tomme Brulee", "Tomme d'Abondance", "Tomme de Chevre",
//            "Tomme de Romans", "Tomme de Savoie", "Tomme des Chouans",
//            "Tommes", "Torta del Casar", "Toscanello", "Touree de L'Aubier",
//            "Tourmalet", "Trappe (Veritable)", "Trois Cornes De Vendee",
//            "Tronchon", "Trou du Cru", "Truffe", "Tupi", "Turunmaa",
//            "Tymsboro", "Tyn Grug", "Tyning", "Ubriaco", "Ulloa",
//            "Vacherin-Fribourgeois", "Valencay", "Vasterbottenost", "Venaco",
//            "Vendomois", "Vieux Corse", "Vignotte", "Vulscombe",
//            "Waimata Farmhouse Blue", "Washed Rind Cheese (Australian)",
//            "Waterloo", "Weichkaese", "Wellington", "Wensleydale",
//            "White Stilton", "Whitestone Farmhouse", "Wigmore",
//            "Woodside Cabecou", "Xanadu", "Xynotyro", "Yarg Cornish",
//            "Yarra Valley Pyramid", "Yorkshire Blue", "Zamorano",
//            "Zanetti Grana Padano", "Zanetti Parmigiano Reggiano"}; 
//}

import java.util.List;

import moishd.android.games.SimonPro;
import moishd.client.dataObjects.ClientMoishdUser;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AllOnlineUsersActivity extends Activity {
	
	protected String authString;
	protected String game_id;
	protected int currentClickPosition;
	private static final String GOOGLE_AUTH_PREF = "google_authentication";
	private static final String GOOGLE_AUTH_STRING = "auth_String";
	private static final String C2DM_PREF = "c2dm_registeration";
	private static final String C2DM_REGISTERED_STRING = "auth_String";

	private static List<ClientMoishdUser> moishdUsers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		authString = getGoogleAuthString();
		
		boolean isRegistered = isC2DMRegistered();
		if (!isRegistered){
			registerC2DM();
		}
			
		moishdUsers = AndroidUtility.getAllUsers(authString);

		setContentView(R.layout.all_users_layout);
		ListView l1 = (ListView) findViewById(R.id.allUsersListView);

		l1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				currentClickPosition = arg2;
				inviteUserToMoishDialog();
			}});
		l1.setAdapter(new EfficientAdapter(this));
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.trophies:
			return true;
		case R.id.welcomeScreen:
			WelcomeScreenActivity.facebookLogout(null);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume (){
		super.onResume();

		game_id = getIntent().getStringExtra("push_game_id");
		String action = getIntent().getStringExtra("Action");
		if (action.equals("game_invitation")){
			getInvitation();
		}
		else if (action.equals("game_declined")){
			userDeclinedToMoishDialog();
			game_id = null;
		}
		else if (action.equals("game_start")){
			startGame();
		}
		
	}
	
	@Override
	protected void onDestroy (){
		super.onDestroy();
		unregisterC2DM();
	}
	

	private void inviteUserToMoishDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You've invited  " + moishdUsers.get(currentClickPosition).getUserNick() + " to Moish. Continue?")
		.setCancelable(false)
		.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				inviteUserToMoish(moishdUsers.get(currentClickPosition));
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();

	}

	private void inviteUserToMoish(ClientMoishdUser user){

		game_id = AndroidUtility.inviteUser(user, authString);

	}

	private void getInvitation(){

		ClientMoishdUser user = AndroidUtility.retrieveInvitation(game_id, authString);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You've been invited by " + user.getUserNick() + " to Moish.")
		.setCancelable(false)
		.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendInvitationResponse("Accept");	
				dialog.cancel();
			}
		})
		.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendInvitationResponse("Decline");	
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();
	}

	private boolean sendInvitationResponse(String response){
		return AndroidUtility.sendInvitationResponse(game_id, response, authString);

	}
	
	private void userDeclinedToMoishDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your invitation has been declined")
		.setCancelable(false)
		.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();  
		alert.show();

	}
	
	private void startGame(){
		
		Intent intent = new Intent(this, SimonPro.class);
		startActivity(intent);
	}
	

	private String getGoogleAuthString() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(GOOGLE_AUTH_PREF,Context.MODE_PRIVATE);
		String authString = prefs.getString(GOOGLE_AUTH_STRING, null);

		return authString;
	}
	

	private boolean isC2DMRegistered() {

		Context context = getApplicationContext();
		final SharedPreferences prefs = context.getSharedPreferences(C2DM_PREF,Context.MODE_PRIVATE);
		boolean isRegistered = prefs.getBoolean(C2DM_REGISTERED_STRING, false);

		return isRegistered;
	}
	
	private void registerC2DM() {
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", "app.moishd@gmail.com");
		startService(registrationIntent);
		
		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(C2DM_PREF,Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(C2DM_REGISTERED_STRING, true);
		editor.commit();
		
		Log.d("TEST","Resgistering...");
		
	}
	
	private void unregisterC2DM() {
		
		Context context = getApplicationContext();
		SharedPreferences prefs = context.getSharedPreferences(C2DM_PREF,Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(C2DM_REGISTERED_STRING, false);
		editor.commit();
				
	}

	private static class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Bitmap userIcon1;
		private Bitmap userIcon2;
		private Bitmap userIcon3;
		private Bitmap userIcon4;
		private Bitmap userRank0;
		private Bitmap userRank1;
		private Bitmap userRank2;
		private Bitmap userRank3;
		//private Bitmap userRank4;
		//private Bitmap userRank5;

		//private Bitmap moishd_logo;

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);

			// Icons bound to the rows.
			userIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.user_icon1);
			userIcon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.user_icon2);
			userIcon3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.user_icon3);
			userIcon4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.user_icon4);
			userRank0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_0);
			userRank1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_1);
			userRank2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_2);
			userRank3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_3);
			//userRank4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_4);
			//userRank5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.rank_5);

			//moishd_logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.moishd_logo);
		}

		public int getCount() {
			return moishdUsers.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.all_users_list_item, null);

				holder = new ViewHolder();
				holder.userName = (TextView) convertView.findViewById(R.id.text);
				holder.userPicture = (ImageView) convertView.findViewById(R.id.userPicture);
				holder.userRank = (ImageView) convertView.findViewById(R.id.userRank);
				//holder.moishdButton = (ImageButton) convertView.findViewById(R.id.moishdButton);

				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.userName.setText(moishdUsers.get(position).getUserNick());
			if (position % 4 == 0){
				holder.userPicture.setImageBitmap(userIcon1);
				holder.userRank.setImageBitmap(userRank0);
			}
			else if (position % 4 == 1){
				holder.userPicture.setImageBitmap(userIcon2);
				holder.userRank.setImageBitmap(userRank1);
			}
			else if (position % 4 == 2){
				holder.userPicture.setImageBitmap(userIcon3);
				holder.userRank.setImageBitmap(userRank2);
			}
			else{
				holder.userPicture.setImageBitmap(userIcon4);
				holder.userRank.setImageBitmap(userRank3);
			}

			return convertView;
		}
		static class ViewHolder {
			TextView userName;
			ImageView userPicture;
			ImageView userRank;
			//ImageButton moishdButton;
		}
	}
}
