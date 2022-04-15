package de.uniba.dsg.charts.resources;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import de.uniba.dsg.charts.CustomSpotifyApi;
import de.uniba.dsg.charts.exceptions.ClientRequestException;
import de.uniba.dsg.charts.exceptions.RemoteApiException;
import de.uniba.dsg.charts.exceptions.ResourceNotFoundException;
import de.uniba.dsg.charts.interfaces.ChartsAPI;
import de.uniba.dsg.charts.models.ErrorMessage;
import de.uniba.dsg.charts.models.TrackResponse;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("charts")
public class ChartsResource implements ChartsAPI {

    @Override
    @GET
    @Path("/{artist-id}")
    public List<TrackResponse> getTopTracks(@PathParam("artist-id") String artistId) {

        if (artistId == null) {
            throw new ClientRequestException(new ErrorMessage("Required query parameter is missing: artist"));
        }

        CountryCode germanyCode = CountryCode.getByCode("DE");
        GetArtistsTopTracksRequest request = CustomSpotifyApi.getInstance().getArtistsTopTracks(artistId, germanyCode).build();

        try {
            Track[] tracks = request.execute();

            if (tracks == null) {
                throw new ResourceNotFoundException(new ErrorMessage(String.format("No matching artist found for id: %s", artistId)));
            }

            if (tracks.length == 0) {
                throw new ResourceNotFoundException(new ErrorMessage("No tracks found for given artist"));
            }

            // Transform album objects into release objects
            List<TrackResponse> responses = new ArrayList<>();
            for (Track track: tracks) {
                TrackResponse response = new TrackResponse();
                response.setTitle(track.getName());
                List<String> artistNames = Arrays.stream(track.getArtists()).map(ArtistSimplified::getName).collect(Collectors.toList());
                String artists = String.join(", ", artistNames);
                response.setArtist(artists);
                response.setId(track.getId());

                responses.add(response);
            }

            return responses;

        } catch (com.wrapper.spotify.exceptions.detailed.NotFoundException e) {
            throw new ResourceNotFoundException(new ErrorMessage(e.getMessage()));
        } catch (BadRequestException e) {
            throw new ClientRequestException(new ErrorMessage(e.getMessage()));
        } catch (SpotifyWebApiException | IOException e) {
            throw new RemoteApiException(new ErrorMessage(e.getMessage()));
        }
    }

}
