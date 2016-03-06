package net.longfalcon.web.api.xml.caps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class SearchingType {

    @XmlElement(name = "search")
    protected SearchType searchType;

    @XmlElement(name = "tv-search")
    protected TvSearchType tvSearchType;

    @XmlElement(name = "movie-search")
    protected MovieSearchType movieSearchType;

    @XmlElement(name = "audio-search")
    protected AudioSearchType audioSearchType;

    public SearchingType() {
    }

    public SearchingType(boolean searchAvailable, boolean tvSearchAvailable, boolean movieSearchAvailable, boolean audioSearchAvailable) {
        searchType = new SearchType(searchAvailable);
        tvSearchType = new TvSearchType(tvSearchAvailable);
        movieSearchType = new MovieSearchType(movieSearchAvailable);
        audioSearchType = new AudioSearchType(audioSearchAvailable);
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public TvSearchType getTvSearchType() {
        return tvSearchType;
    }

    public void setTvSearchType(TvSearchType tvSearchType) {
        this.tvSearchType = tvSearchType;
    }

    public MovieSearchType getMovieSearchType() {
        return movieSearchType;
    }

    public void setMovieSearchType(MovieSearchType movieSearchType) {
        this.movieSearchType = movieSearchType;
    }

    public AudioSearchType getAudioSearchType() {
        return audioSearchType;
    }

    public void setAudioSearchType(AudioSearchType audioSearchType) {
        this.audioSearchType = audioSearchType;
    }
}
