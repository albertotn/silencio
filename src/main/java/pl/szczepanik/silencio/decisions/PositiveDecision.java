package pl.szczepanik.silencio.decisions;

import pl.szczepanik.silencio.api.Decision;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Implementation of {@link Decision} that always returns positive decision.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class PositiveDecision implements Decision {

    @Override
    public boolean decide(Key key, Value value) {
        return true;
    }

}
