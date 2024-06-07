package org.odds.nlp;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {
    public static final String[] PHRASES = new String[] {
            "Many people say that life isn't like a bed of roses. I beg to differ. I think that life is quite like a bed of roses. Just like life, a bed of roses looks pretty on the outside, but when you're in it, you find that it is nothing but thorns and pain. I myself have been pricked quite badly.",
            "I guess we could discuss the implications of the phrase \"meant to be.\" That is if we wanted to drown ourselves in a sea of backwardly referential semantics and other mumbo-jumbo. Maybe such a discussion would result in the determination that \"meant to be\" is exactly as meaningless a phrase as it seems to be, and that none of us is actually meant to be doing anything at all. But that's my existential underpants underpinnings showing. It's the way the cookie crumbles. And now I want a cookie.",
            "Her mom had warned her. She had been warned time and again, but she had refused to believe her. She had done everything right and she knew she would be rewarded for doing so with the promotion. So when the promotion was given to her main rival, it not only stung, it threw her belief system into disarray. It was her first big lesson in life, but not the last.",
            "The paper was blank. It shouldn't have been. There should have been writing on the paper, at least a paragraph if not more. The fact that the writing wasn't there was frustrating. Actually, it was even more than frustrating. It was downright distressing.",
            "I'm going to hire professional help tomorrow. I can't handle this anymore. She fell over the coffee table and now there is blood in her catheter. This is much more than I ever signed up to do.",
            "Time is all relative based on age and experience. When you are a child an hour is a long time to wait but a very short time when that’s all the time you are allowed on your iPad. As a teenager time goes faster the more deadlines you have and the more you procrastinate. As a young adult, you think you have forever to live and don’t appreciate the time you spend with others. As a middle-aged adult, time flies by as you watch your children grow up. And finally, as you get old and you have fewer responsibilities and fewer demands on you, time slows. You appreciate each day and are thankful you are alive. An hour is the same amount of time for everyone yet it can feel so different in how it goes by.",
            "The time to take action was now. All three men knew in their hearts this was the case, yet none of them moved a muscle to try. They were all watching and waiting for one of the others to make the first move so they could follow a step or two behind and help. The situation demanded a leader and all three men were followers.",
            "MaryLou wore the tiara with pride. There was something that made doing anything she didn't really want to do a bit easier when she wore it. She really didn't care what those staring through the window were thinking as she vacuumed her apartment.",
            "Rhonda prided herself on always taking the path less traveled. She'd decided to do this at an early age and had continued to do so throughout her entire life. It was a point of pride and she would explain to anyone who would listen that doing so was something that she'd made great efforts to always do. She'd never questioned this decision until her five-year-old niece asked her, \"So, is this why your life has been so difficult?\" and Rhonda didn't have an answer for her.",
            "The fog was as thick as pea soup. This was a problem. Gary was driving but couldn't see a thing in front of him. He knew he should stop, but the road was narrow so if he did, it would be right in the center of the road. He was sure that another car would end up rear-ending him, so he continued forward despite the lack of visibility. This was an unwise move.",
            "She patiently waited for his number to be called. She had no desire to be there, but her mom had insisted that she go. She's resisted at first, but over time she realized it was simply easier to appease her and go. Mom tended to be that way. She would keep insisting until you wore down and did what she wanted. So, here she sat, patiently waiting for her number to be called."
    };

    public static void main(String[] args) {
        Properties props = new Properties();

        // list of annotators to run
        props.setProperty("annotators", "tokenize,pos,lemma,sentiment,relation");
        props.setProperty("tokenize.options", "splitHyphenated=false,americanize=false");

        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create a document object
        CoreDocument doc = pipeline.processToCoreDocument(PHRASES[1]);
        pipeline.annotate(doc);

        doc.tokens().forEach((token) -> {
            System.out.printf("%s\t%s\t%s\t%d\t%d\n", token.word(), token.lemma(), token.tag(), token.beginPosition(), token.endPosition());
        });

        doc.sentences().forEach((sentence) -> {
            System.out.printf("%s\n", sentence);
        });
    }
}
