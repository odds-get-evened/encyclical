package org.odds.nlp;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.Annotator;

import java.util.Set;

public class PlotAnnotator implements Annotator {
    @Override
    public void annotate(Annotation annotation) {

    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
        return Set.of();
    }

    @Override
    public Set<Class<? extends CoreAnnotation>> requires() {
        return Set.of();
    }
}
