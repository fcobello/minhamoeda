package br.com.cobello.minhamoeda.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil
{
	private static final Logger LOG = Logger.getLogger(JSONUtil.class.getName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
    * Converte um objeto para uma representação JSON.
    *
    * @param t
    * @return String
    * @throws JsonParseException
    */
    public static <T> String objectToJson (final T t) throws JsonParseException
    {
        try
        {
            return new ObjectMapper().writeValueAsString(t);
        }
        catch (final Exception e)
        {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new JsonParseException(null, e.getMessage());
        }
    }

    /**
    * Converte uma string de representação JSON para um objeto. Suporta objetos
    * do tipo lista.
    *
    * @param json
    * @param klass
    * @param isList
    * @return Object
    * @throws JsonParseException
    */
    @SuppressWarnings ("unchecked")
    public static <T> T jsonToObject (final String json, final Class<?> klass, final boolean isList) throws JsonParseException
    {
        try
        {
            final ObjectMapper mapper = new ObjectMapper();
            sdf.setLenient(false);
            mapper.setDateFormat(sdf);

            if (isList)
            {
                final JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, klass);
                return (T) mapper.readValue(json, type);
            }

            return (T) mapper.readValue(json, klass);
        }
        catch (final Exception e)
        {
        	LOG.log(Level.SEVERE, e.getMessage(), e);
            String msg = e.getMessage();

            try
            {
                msg = e.getMessage().split("\n")[0];
            }
            catch (final Exception ex)
            {
                // OK
            }

            throw new JsonParseException(null, msg);
        }
    }
}