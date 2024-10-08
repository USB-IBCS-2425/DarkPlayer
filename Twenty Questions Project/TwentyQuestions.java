import java.util.*;

class TwentyQuestions {
    ArrayList<String> pos = new ArrayList<String>();
    String ans;
    ArrayList<String> cur_pos;
    int questions;

    public TwentyQuestions() {
        pos.addAll(Arrays.asList("Dallas Cowboys", "New York Giants", "Philadelphia Eagles", "Washington Commanders",  // NFC East
            "Green Bay Packers", "Minnesota Vikings", "Detroit Lions", "Chicago Bears",           // NFC North
            "Tampa Bay Buccaneers", "New Orleans Saints", "Atlanta Falcons", "Carolina Panthers", // NFC South
            "San Francisco 49ers", "Seattle Seahawks", "Los Angeles Rams", "Arizona Cardinals",   // NFC West

            // AFC Teams
            "Buffalo Bills", "Miami Dolphins", "New England Patriots", "New York Jets",           // AFC East
            "Pittsburgh Steelers", "Cleveland Browns", "Baltimore Ravens", "Cincinnati Bengals",  // AFC North
            "Kansas City Chiefs", "Las Vegas Raiders", "Los Angeles Chargers", "Denver Broncos",  // AFC West
            "Indianapolis Colts", "Houston Texans", "Tennessee Titans", "Jacksonville Jaguars"    // AFC South
        ));
        ans = "";
        cur_pos = new ArrayList<String>(pos); 
        questions = 0;
    }

    public void askQuestion() {
    	String[] queses = {"Is the team in the NFC?", "Is the team in the North or South divisions of their conference?",
    			"Has the team won a Super Bowl?", "Does the team have warm colors (red or orange) as their primary color on any of their jerseys?",
    			"Is the team name an animal?", "Is the team's home stadium in the Western US (relative to Mississipi River)"
		};
        
        Scanner s = new Scanner(System.in);
        

         System.out.println("Answer the following the questions with yes or no.");
        while (cur_pos.size() > 1 && questions < 6) {
        	System.out.println(queses[questions]);
        	String res = s.nextLine();
        	updateAnswers((res.equals("yes")), queses[questions]);
        	questions++;
        }
        if (cur_pos.size() == 0) {
        	ans = "Described team doesn't exist";
        } else {
        	ans = cur_pos.get(0);
        }
        
        

    }

    public void updateAnswers(boolean res, String ques) {
    	// Question 1
        if (ques.equals("Is the team in the NFC?")) {

            if (res) {
                cur_pos.removeAll(Arrays.asList("Buffalo Bills", "Miami Dolphins", "New England Patriots", "New York Jets",           // AFC East
                    "Pittsburgh Steelers", "Cleveland Browns", "Baltimore Ravens", "Cincinnati Bengals",  // AFC North
                    "Kansas City Chiefs", "Las Vegas Raiders", "Los Angeles Chargers", "Denver Broncos",  // AFC West
                    "Indianapolis Colts", "Houston Texans", "Tennessee Titans", "Jacksonville Jaguars"
            ));
             	
            
            } else {
                cur_pos.removeAll(Arrays.asList("Dallas Cowboys", "New York Giants", "Philadelphia Eagles", "Washington Commanders",  // NFC East
                    "Green Bay Packers", "Minnesota Vikings", "Detroit Lions", "Chicago Bears",           // NFC North
                    "Tampa Bay Buccaneers", "New Orleans Saints", "Atlanta Falcons", "Carolina Panthers", // NFC South
                    "San Francisco 49ers", "Seattle Seahawks", "Los Angeles Rams", "Arizona Cardinals")); // NFC West
            }
        }
        // Question 2
        else if (ques.equals("Is the team in the North or South divisions of their conference?")) {
        	if (res) {
        		cur_pos.removeAll(Arrays.asList("Buffalo Bills", "Miami Dolphins", "New England Patriots", "New York Jets",           // AFC East
                    "Kansas City Chiefs", "Las Vegas Raiders", "Los Angeles Chargers", "Denver Broncos",  // AFC West
                    "San Francisco 49ers", "Seattle Seahawks", "Los Angeles Rams", "Arizona Cardinals", // NFC West
                    "Dallas Cowboys", "New York Giants", "Philadelphia Eagles", "Washington Commanders"  // NFC East
                ));
                    
        	} else {
        		cur_pos.removeAll(Arrays.asList(
        			"Pittsburgh Steelers", "Cleveland Browns", "Baltimore Ravens", "Cincinnati Bengals",  // AFC North
        			"Indianapolis Colts", "Houston Texans", "Tennessee Titans", "Jacksonville Jaguars", // AFC South
        			"Tampa Bay Buccaneers", "New Orleans Saints", "Atlanta Falcons", "Carolina Panthers", // NFC South
        			"Green Bay Packers", "Minnesota Vikings", "Detroit Lions", "Chicago Bears"           // NFC North
        		));
        	}
        }
        // Question 3
        else if (ques.equals("Has the team won a Super Bowl?")) {
        	if (res) {
        		cur_pos.removeAll(Arrays.asList("Minnesota Vikings", "Detroit Lions", "Atlanta Falcons", "Carolina Panthers", 
        			"Arizona Cardinals", "Buffalo Bills", "Cleveland Browns", "Cincinnati Bengals", "Houston Texans", 
        			"Jacksonville Jaguars", "Tennessee Titans", "Los Angeles Chargers"
                ));
        	} else {
        		cur_pos.removeAll(Arrays.asList(
        			"Dallas Cowboys", "New York Giants", "Philadelphia Eagles", "Green Bay Packers", "Chicago Bears", 
        			"Tampa Bay Buccaneers", "New Orleans Saints", "San Francisco 49ers", "Seattle Seahawks", "Los Angeles Rams", 
        			"Baltimore Ravens", "Pittsburgh Steelers", "Kansas City Chiefs", "Miami Dolphins", "New England Patriots", 
        			"Denver Broncos", "Indianapolis Colts", "Washington Commanders", "Las Vegas Raiders", "New York Jets"
        		));
        	}
        }
        //Question 4
        else if (ques.equals("Does the team have warm colors (red or orange) as their primary color on any of their jerseys?")) {
        	if (res) {
        		cur_pos.removeAll(Arrays.asList("Pittsburgh Steelers", "Minnesota Vikings", "Green Bay Packers","Atlanta Falcons", "Dallas Cowboys", 
	                "Philadelphia Eagles", "Detroit Lions", "Carolina Panthers", "Seattle Seahawks", "Los Angeles Rams", "Baltimore Ravens", "Miami Dolphins", 
	                "Tennessee Titans", "Indianapolis Colts", "Los Angeles Chargers", "Jacksonville Jaguars"
        		));
        	} else {
        		cur_pos.removeAll(Arrays.asList("Chicago Bears", "Arizona Cardinals", "San Francisco 49ers", "Kansas City Chiefs", 
	        		"Tampa Bay Buccaneers", "Buffalo Bills", "New England Patriots", "Houston Texans", "Washington Commanders", 
	        		"Cincinnati Bengals", "Cleveland Browns", "Denver Broncos", "New York Giants", "Las Vegas Raiders"
        		));
        	}
        	
        }
        	
        //Question 5
        else if (ques.equals("Is the team name an animal?")) {
        	if (res) {
        		cur_pos.removeAll(Arrays.asList("Dallas Cowboys", "New York Giants", "San Francisco 49ers", "Kansas City Chiefs", "Tampa Bay Buccaneers", 
        			"New England Patriots", "Washington Commanders", "Cleveland Browns", "Green Bay Packers", "Minnesota Vikings", "Pittsburgh Steelers", 
        			"Indianapolis Colts", "Las Vegas Raiders", "Los Angeles Chargers","Tennessee Titans", "Houston Texans", "Buffalo Bills"
        		));
        	} else {
        		
        		cur_pos.removeAll(Arrays.asList("Arizona Cardinals", "Atlanta Falcons", "Philadelphia Eagles", "Detroit Lions", "Chicago Bears", 
	        		"Carolina Panthers", "Seattle Seahawks", "Los Angeles Rams", "Baltimore Ravens", "Miami Dolphins", "Jacksonville Jaguars", 
	        		"Cincinnati Bengals", "Denver Broncos" 
        		));
        	}
        	
        }
        //Question 6
        else if (ques.equals("Is the team's home stadium in the Western US (relative to Mississipi River)")) {
        	if (res) {
        		cur_pos.removeAll(Arrays.asList("Atlanta Falcons", "Carolina Panthers", "Tampa Bay Buccaneers", "New Orleans Saints", "Philadelphia Eagles", 
        			"New York Giants", "Washington Commanders", "Detroit Lions", "Chicago Bears", "Green Bay Packers", "Minnesota Vikings", "Buffalo Bills", 
        			"Miami Dolphins", "New England Patriots", "New York Jets", "Baltimore Ravens", "Pittsburgh Steelers", "Cleveland Browns", "Cincinnati Bengals", 
        			"Indianapolis Colts", "Tennessee Titans", "Jacksonville Jaguars"
        		));
        	} else {
        		cur_pos.removeAll(Arrays.asList("Arizona Cardinals", "San Francisco 49ers", "Seattle Seahawks", "Los Angeles Rams", "Los Angeles Chargers", "Kansas City Chiefs", 
        			"Denver Broncos", "Las Vegas Raiders", "Dallas Cowboys", "Houston Texans"
        		));
        	}
        }




    }

    public static void main(String[] args) {
        TwentyQuestions game = new TwentyQuestions();
        game.askQuestion();
        System.out.println(game.ans);
    }
}