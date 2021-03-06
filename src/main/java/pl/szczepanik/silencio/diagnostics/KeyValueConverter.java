package pl.szczepanik.silencio.diagnostics;

import pl.szczepanik.silencio.api.Converter;
import pl.szczepanik.silencio.core.Key;
import pl.szczepanik.silencio.core.Value;

/**
 * Diagnostic converter that returns new instance of {@link Value} object created from passed key.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public final class KeyValueConverter implements Converter {

    // Limits the access only to diagnostic package.
    KeyValueConverter() {
    }

    @Override
    public Value convert(Key key, Value value) {
        return new Value(key.getKey());
    }

    @Override
    public void init() {
        // This method is intentionally empty, because this class is stateless
    }

}
