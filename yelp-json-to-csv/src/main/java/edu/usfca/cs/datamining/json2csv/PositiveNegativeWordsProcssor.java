package edu.usfca.cs.datamining.json2csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;

public class PositiveNegativeWordsProcssor {
	/**
	 * brained stormed all the words.
	 * */
	private static final char[] ordinaryChars = new char[] { '\'', '\"', '/', '.' };
	private static final String[] positiveWords = new String[] { "absolutely", "adorable", "accepted", "acclaimed",
			"accomplish", "accomplishment", "achievement", "action", "active", "admire", "adventure", "affirmative",
			"affluent", "agree", "agreeable", "amazing", "angelic", "appealing", "approve", "aptitude", "attractive",
			"awesome", "beaming", "beautiful", "believe", "beneficial", "bliss", "bountiful", "bounty", "brave",
			"bravo", "brilliant", "bubbly", "calm", "celebrated", "certain", "champ", "champion", "charming", "cheery",
			"choice", "classic", "classical", "clean", "commend", "composed", "congratulation", "constant", "cool",
			"courageous", "creative", "cute", "dazzling", "delight", "delightful", "distinguished", "divine", "earnest",
			"easy", "ecstatic", "effective", "effervescent", "efficient", "effortless", "electrifying", "elegant",
			"enchanting", "encouraging", "endorsed", "energetic", "energized", "engaging", "enthusiastic", "essential",
			"esteemed", "ethical", "excellent", "exciting", "exquisite", "fabulous", "fair", "familiar", "famous",
			"fantastic", "favorable", "fetching", "fine", "fitting", "flourishing", "fortunate", "free", "fresh",
			"friendly", "fun", "funny", "generous", "genius", "genuine", "giving", "glamorous", "glowing", "good",
			"gorgeous", "graceful", "great", "green", "grin", "growing", "handsome", "happy", "harmonious", "healing",
			"healthy", "hearty", "heavenly", "honest", "honorable", "honored", "hug", "idea", "ideal", "imaginative",
			"imagine", "impressive", "independent", "innovate", "innovative", "instant", "instantaneous", "instinctive",
			"intuitive", "intellectual", "intelligent", "inventive", "jovial", "joy", "jubilant", "keen", "kind",
			"knowing", "knowledgeable", "laugh", "legendary", "light", "learned", "lively", "lovely", "lucid", "lucky",
			"luminous", "marvelous", "masterful", "meaningful", "merit", "meritorious", "miraculous", "motivating",
			"moving", "natural", "nice", "novel", "now", "nurturing", "nutritious", "okay", "one",
			"one-hundred percent", "open", "optimistic", "paradise", "perfect", "phenomenal", "pleasurable",
			"plentiful", "pleasant", "poised", "polished", "popular", "positive", "powerful", "prepared", "pretty",
			"principled", "productive", "progress", "prominent", "protected", "proud", "quality", "quick", "quiet",
			"ready", "reassuring", "refined", "refreshing", "rejoice", "reliable", "remarkable", "resounding",
			"respected", "restored", "reward", "rewarding", "right", "robust", "safe", "satisfactory", "secure",
			"seemly", "simple", "skilled", "skillful", "smile", "soulful", "sparkling", "special", "spirited",
			"spiritual", "stirring", "stupendous", "stunning", "success", "successful", "sunny", "super", "superb",
			"supporting", "surprising", "terrific", "thorough", "thrilling", "thriving", "tops", "tranquil",
			"transforming", "transformative", "trusting", "truthful", "unreal", "unwavering", "up", "upbeat", "upright",
			"upstanding", "valued", "vibrant", "victorious", "victory", "vigorous", "virtuous", "vital", "vivacious",
			"wealthy", "welcome", "well", "whole", "wholesome", "willing", "wonderful", "wondrous", "worthy", "wow",
			"yes", "yummy", "zeal", "zealous", };
	private static final HashSet<String> positiveWordsSet = new HashSet<String>(Arrays.asList(positiveWords));

	private static final String[] negativeWords = new String[] { "abysmal", "adverse", "alarming", "angry", "annoy",
			"anxious", "apathy", "appalling", "atrocious", "awful", "bad", "banal", "barbed", "belligerent", "bemoan",
			"beneath", "boring", "broken", "callous", "can't", "clumsy", "coarse", "cold", "cold-hearted", "collapse",
			"confused", "contradictory", "contrary", "corrosive", "corrupt", "crazy", "creepy", "criminal", "cruel",
			"cry", "cutting", "dead", "decaying", "damage", "damaging", "dastardly", "deplorable", "depressed",
			"deprived", "deformed", "deny", "despicable", "detrimental", "dirty", "disease", "disgusting", "disheveled",
			"dishonest", "dishonorable", "dismal", "distress", "don't", "dreadful", "dreary", "enraged", "eroding",
			"evil", "fail", "faulty", "fear", "feeble", "fight", "filthy", "foul", "frighten", "frightful", "gawky",
			"ghastly", "grave", "greed", "grim", "grimace", "gross", "grotesque", "gruesome", "guilty", "haggard",
			"hard", "hard-hearted", "harmful", "hate", "hideous", "homely", "horrendous", "horrible", "hostile", "hurt",
			"hurtful", "icky", "ignore", "ignorant", "ill", "immature", "imperfect", "impossible", "inane", "inelegant",
			"infernal", "injure", "injurious", "insane", "insidious", "insipid", "jealous", "junky", "lose", "lousy",
			"lumpy", "malicious", "mean", "menacing", "messy", "misshapen", "missing", "misunderstood", "moan", "moldy",
			"monstrous", "naive", "nasty", "naughty", "negate", "negative", "never", "no", "nobody", "nondescript",
			"nonsense", "not", "noxious", "objectionable", "odious", "offensive", "old", "oppressive", "pain",
			"perturb", "pessimistic", "petty", "plain", "poisonous", "poor", "prejudice", "questionable", "quirky",
			"quit", "reject", "renege", "repellant", "reptilian", "repulsive", "repugnant", "revenge", "revolting",
			"rocky", "rotten", "rude", "ruthless", "sad", "savage", "scare", "scary", "scream", "severe", "shoddy",
			"shocking", "sick", "sickening", "sinister", "slimy", "smelly", "sobbing", "sorry", "spiteful", "sticky",
			"stinky", "stormy", "stressful", "stuck", "stupid", "substandard", "suspect", "suspicious", "tense",
			"terrible", "terrifying", "threatening", "ugly", "undermine", "unfair", "unfavorable", "unhappy",
			"unhealthy", "unjust", "unlucky", "unpleasant", "upset", "unsatisfactory", "unsightly", "untoward",
			"unwanted", "unwelcome", "unwholesome", "unwieldy", "unwise", "upset", "vice", "vicious", "vile",
			"villainous", "vindictive", "wary", "weary", "wicked", "woeful", "worthless", "wound", "yell", "yucky",
			"zero", };
	private static final HashSet<String> negativeWordsSet = new HashSet<String>(Arrays.asList(negativeWords));

	public static ReviewFeatures evaluate(String text) {
		Reader reader = new StringReader(text);
		StreamTokenizer st = new StreamTokenizer(new BufferedReader(reader));

		for (char c : ordinaryChars) {
			st.ordinaryChar(c);
		}

		ReviewFeatures features = new ReviewFeatures();
		try {
			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				switch (st.ttype) {
				case StreamTokenizer.TT_EOL:
					break;
				case StreamTokenizer.TT_NUMBER:
					break;
				case StreamTokenizer.TT_WORD:
					if (positiveWordsSet.contains(st.sval)) {
						features.positive++;
						features.positiveNegativeSum++;
					} else if (negativeWordsSet.contains(st.sval)) {
						features.negative++;
						features.positiveNegativeSum--;
					}
					features.wordCount++;
					break;
				default:
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return features;
	}
}
