package nl.han.ica.yaeger;

import nl.han.ica.yaeger.entities.EntityCollectionStatistics;

/**
 * A {@link StatisticsObserver} will function as the {@code Observer} from the Observable-pattern, for changes in
 * the {@link EntityCollectionStatistics}.
 */
@FunctionalInterface
public interface StatisticsObserver {

    /**
     * Is called by the observed {@link nl.han.ica.yaeger.entities.EntityCollection}.
     *
     * @param statistics An instance of {@link EntityCollectionStatistics} that encapsulates the latest statistical
     *                   information regarding the observed {@link nl.han.ica.yaeger.entities.EntityCollection}.
     */
    void update(EntityCollectionStatistics statistics);
}
