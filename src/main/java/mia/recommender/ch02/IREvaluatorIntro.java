package mia.recommender.ch02;

import mia.utils.ResourceUtil;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;

class IREvaluatorIntro {

    private IREvaluatorIntro() {
    }

    public static void main(String[] args) throws Exception {
        RandomUtils.useTestSeed();

        String introCsv = ResourceUtil.getResource("ch02/intro.csv");

        File modelFile = null;
        if (args.length > 0)
            modelFile = new File(args[0]);
        if (modelFile == null || !modelFile.exists())
            modelFile = new File(introCsv);
        if (!modelFile.exists()) {
            System.err.println("Please, specify name of file, or put file 'input.csv' into current directory!");
            System.exit(1);
        }
        DataModel model = new FileDataModel(modelFile);

        RecommenderIRStatsEvaluator evaluator =
                new GenericRecommenderIRStatsEvaluator();
        // Build the same recommender for testing that we did last time:
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood =
                        new NearestNUserNeighborhood(2, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // Evaluate precision and recall "at 2":
        IRStatistics stats = evaluator.evaluate(recommenderBuilder,
                null, model, null, 2,
                GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
                1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());
    }
}
