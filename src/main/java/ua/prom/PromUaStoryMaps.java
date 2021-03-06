package ua.prom;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStoryMaps;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.util.List;

import static java.util.Arrays.asList;
import org.jbehave.core.Embeddable;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * {@link Embeddable} class to map stories specified by {@link #storyPaths()},
 * using the meta filters specified by {@link #metaFilters()}.
 * </p>
 */
public class PromUaStoryMaps extends JUnitStoryMaps {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PromUaStoryMaps() {
        configuredEmbedder().useMetaFilters(metaFilters());
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
            .useStoryParser(new RegexStoryParser(new ExamplesTableFactory(new LoadFromClasspath(this.getClass()))))
            .useStoryReporterBuilder(new StoryReporterBuilder()
                .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass())));
    }

    @Override
    protected List<String> metaFilters() {
        // will be specified by values in the pom.xml file when run from Maven
        return asList();
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/stories/*.story", "");
    }
}
