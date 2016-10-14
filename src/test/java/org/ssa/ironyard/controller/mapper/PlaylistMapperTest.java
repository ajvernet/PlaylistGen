package org.ssa.ironyard.controller.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.ssa.ironyard.controller.mapper.PlaylistMapper;

public class PlaylistMapperTest {

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    
    String responseJson;
    
    @Before
    public void setUp() {
                
        responseJson =
          
           "{\"id\":21"
           + ",\"loaded\":false"
           + ",\"name\":\"alex\""
           + ",\"user\":{\"id\":null,\"loaded\":false,\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null,\"address\":null}"
           + ",\"targetDuration\":3600"
           + ",\"currentDuration\":43"
           + ",\"episodes\":[{\"id\":1,\"loaded\":false,\"episodeId\":1137,\"name\":\"Test name\""
           + ",\"genreId\":null,\"description\":\"Test description\""
           + ",\"duration\":43"
           + ",\"fileUrl\":\"https://www.test.mp3\",\"show\":{\"id\":86,\"name\":\"KUOW\""
           + ",\"thumbUrl\":\"https://i1.sndcdn.com/avatars-000030391447-yl6p1t-large.jpg\"}}]}";
 
          }

    @Test
    public void testJsonToPlaylist() throws IOException
    {
        MockHttpInputMessage inputMessage = new MockHttpInputMessage(responseJson.getBytes("UTF-8"));
        inputMessage.getHeaders().setContentType(new MediaType("application", "json"));
        PlaylistMapper playlist = (PlaylistMapper) converter.read(PlaylistMapper.class, inputMessage);
        
        assertEquals("alex", playlist.getName());
        
        assertEquals(new Integer(21), playlist.getId());
        assertEquals(new Integer(1), playlist.getEpisodes().get(0).getId());
        assertEquals(new Integer(1137), playlist.getEpisodes().get(0).getEpisodeId());
        assertEquals("Test name", playlist.getEpisodes().get(0).getName());
        assertNull(playlist.getEpisodes().get(0).getGenreId());
        assertEquals(new Integer(43), playlist.getEpisodes().get(0).getDuration());
        assertEquals("https://www.test.mp3", playlist.getEpisodes().get(0).getFileUrl());
        assertEquals(new Integer(86), playlist.getEpisodes().get(0).getShow().getId());
        assertEquals("KUOW", playlist.getEpisodes().get(0).getShow().getName());
        assertEquals("https://i1.sndcdn.com/avatars-000030391447-yl6p1t-large.jpg", playlist.getEpisodes().get(0).getShow().getThumbUrl());
       
    }


}
