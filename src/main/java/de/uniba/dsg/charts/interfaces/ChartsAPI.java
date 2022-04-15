package de.uniba.dsg.charts.interfaces;

import de.uniba.dsg.charts.models.TrackResponse;
import java.util.List;

public interface ChartsAPI {

    List<TrackResponse> getTopTracks(String artistId);
}
