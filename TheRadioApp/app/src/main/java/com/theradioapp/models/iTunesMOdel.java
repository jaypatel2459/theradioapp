package com.theradioapp.models;

import java.util.List;

public class iTunesMOdel {

    /**
     * resultCount : 1
     * results : [{"wrapperType":"track","kind":"song","artistId":"92940","collectionId":568177457,"trackId":568177745,"artistName":"Kem","collectionName":"What Christmas Means","trackName":"Merry Christmas Baby","collectionCensoredName":"What Christmas Means","trackCensoredName":"Merry Christmas Baby","artistViewUrl":"https://itunes.apple.com/us/artist/kem/92940?uo=4&partnerId=11&at=1010l8zK","collectionViewUrl":"https://itunes.apple.com/us/album/merry-christmas-baby/568177457?i=568177745&uo=4&partnerId=11&at=1010l8zK","trackViewUrl":"https://itunes.apple.com/us/album/merry-christmas-baby/568177457?i=568177745&uo=4&partnerId=11&at=1010l8zK","previewUrl":"https://audio-ssl.itunes.apple.com/apple-assets-us-std-000001/Music/f3/cf/ae/mzi.mbjlstdi.aac.p.m4a","artworkUrl30":"https://is2-ssl.mzstatic.com/image/thumb/Music/v4/38/9a/9d/389a9d14-3b47-d775-2c0c-b5e7789ff4d1/source/30x30bb.jpg","artworkUrl60":"https://is2-ssl.mzstatic.com/image/thumb/Music/v4/38/9a/9d/389a9d14-3b47-d775-2c0c-b5e7789ff4d1/source/60x60bb.jpg","artworkUrl100":"https://is2-ssl.mzstatic.com/image/thumb/Music/v4/38/9a/9d/389a9d14-3b47-d775-2c0c-b5e7789ff4d1/source/100x100bb.jpg","collectionPrice":7.99,"trackPrice":1.29,"releaseDate":"2012-10-12T07:00:00Z","collectionExplicitness":"notExplicit","trackExplicitness":"notExplicit","discCount":1,"discNumber":1,"trackCount":10,"trackNumber":8,"trackTimeMillis":166364,"country":"USA","currency":"USD","primaryGenreName":"Christmas","isStreamable":true}]
     */

    private int resultCount;
    private List<ResultsBean> results;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * wrapperType : track
         * kind : song
         * artistId : 92940
         * collectionId : 568177457
         * trackId : 568177745
         * artistName : Kem
         * collectionName : What Christmas Means
         * trackName : Merry Christmas Baby
         * collectionCensoredName : What Christmas Means
         * trackCensoredName : Merry Christmas Baby
         * artistViewUrl : https://itunes.apple.com/us/artist/kem/92940?uo=4&partnerId=11&at=1010l8zK
         * collectionViewUrl : https://itunes.apple.com/us/album/merry-christmas-baby/568177457?i=568177745&uo=4&partnerId=11&at=1010l8zK
         * trackViewUrl : https://itunes.apple.com/us/album/merry-christmas-baby/568177457?i=568177745&uo=4&partnerId=11&at=1010l8zK
         * previewUrl : https://audio-ssl.itunes.apple.com/apple-assets-us-std-000001/Music/f3/cf/ae/mzi.mbjlstdi.aac.p.m4a
         * artworkUrl30 : https://is2-ssl.mzstatic.com/image/thumb/Music/v4/38/9a/9d/389a9d14-3b47-d775-2c0c-b5e7789ff4d1/source/30x30bb.jpg
         * artworkUrl60 : https://is2-ssl.mzstatic.com/image/thumb/Music/v4/38/9a/9d/389a9d14-3b47-d775-2c0c-b5e7789ff4d1/source/60x60bb.jpg
         * artworkUrl100 : https://is2-ssl.mzstatic.com/image/thumb/Music/v4/38/9a/9d/389a9d14-3b47-d775-2c0c-b5e7789ff4d1/source/100x100bb.jpg
         * collectionPrice : 7.99
         * trackPrice : 1.29
         * releaseDate : 2012-10-12T07:00:00Z
         * collectionExplicitness : notExplicit
         * trackExplicitness : notExplicit
         * discCount : 1
         * discNumber : 1
         * trackCount : 10
         * trackNumber : 8
         * trackTimeMillis : 166364
         * country : USA
         * currency : USD
         * primaryGenreName : Christmas
         * isStreamable : true
         */

        private String wrapperType;
        private String kind;
        private String artistId;
        private int collectionId;
        private int trackId;
        private String artistName;
        private String collectionName;
        private String trackName;
        private String collectionCensoredName;
        private String trackCensoredName;
        private String artistViewUrl;
        private String collectionViewUrl;
        private String trackViewUrl;
        private String previewUrl;
        private String artworkUrl30;
        private String artworkUrl60;
        private String artworkUrl100;
        private double collectionPrice;
        private double trackPrice;
        private String releaseDate;
        private String collectionExplicitness;
        private String trackExplicitness;
        private int discCount;
        private int discNumber;
        private int trackCount;
        private int trackNumber;
        private int trackTimeMillis;
        private String country;
        private String currency;
        private String primaryGenreName;
        private boolean isStreamable;

        public String getWrapperType() {
            return wrapperType;
        }

        public void setWrapperType(String wrapperType) {
            this.wrapperType = wrapperType;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getArtistId() {
            return artistId;
        }

        public void setArtistId(String artistId) {
            this.artistId = artistId;
        }

        public int getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(int collectionId) {
            this.collectionId = collectionId;
        }

        public int getTrackId() {
            return trackId;
        }

        public void setTrackId(int trackId) {
            this.trackId = trackId;
        }

        public String getArtistName() {
            return artistName;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public String getCollectionName() {
            return collectionName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }

        public String getTrackName() {
            return trackName;
        }

        public void setTrackName(String trackName) {
            this.trackName = trackName;
        }

        public String getCollectionCensoredName() {
            return collectionCensoredName;
        }

        public void setCollectionCensoredName(String collectionCensoredName) {
            this.collectionCensoredName = collectionCensoredName;
        }

        public String getTrackCensoredName() {
            return trackCensoredName;
        }

        public void setTrackCensoredName(String trackCensoredName) {
            this.trackCensoredName = trackCensoredName;
        }

        public String getArtistViewUrl() {
            return artistViewUrl;
        }

        public void setArtistViewUrl(String artistViewUrl) {
            this.artistViewUrl = artistViewUrl;
        }

        public String getCollectionViewUrl() {
            return collectionViewUrl;
        }

        public void setCollectionViewUrl(String collectionViewUrl) {
            this.collectionViewUrl = collectionViewUrl;
        }

        public String getTrackViewUrl() {
            return trackViewUrl;
        }

        public void setTrackViewUrl(String trackViewUrl) {
            this.trackViewUrl = trackViewUrl;
        }

        public String getPreviewUrl() {
            return previewUrl;
        }

        public void setPreviewUrl(String previewUrl) {
            this.previewUrl = previewUrl;
        }

        public String getArtworkUrl30() {
            return artworkUrl30;
        }

        public void setArtworkUrl30(String artworkUrl30) {
            this.artworkUrl30 = artworkUrl30;
        }

        public String getArtworkUrl60() {
            return artworkUrl60;
        }

        public void setArtworkUrl60(String artworkUrl60) {
            this.artworkUrl60 = artworkUrl60;
        }

        public String getArtworkUrl100() {
            return artworkUrl100;
        }

        public void setArtworkUrl100(String artworkUrl100) {
            this.artworkUrl100 = artworkUrl100;
        }

        public double getCollectionPrice() {
            return collectionPrice;
        }

        public void setCollectionPrice(double collectionPrice) {
            this.collectionPrice = collectionPrice;
        }

        public double getTrackPrice() {
            return trackPrice;
        }

        public void setTrackPrice(double trackPrice) {
            this.trackPrice = trackPrice;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getCollectionExplicitness() {
            return collectionExplicitness;
        }

        public void setCollectionExplicitness(String collectionExplicitness) {
            this.collectionExplicitness = collectionExplicitness;
        }

        public String getTrackExplicitness() {
            return trackExplicitness;
        }

        public void setTrackExplicitness(String trackExplicitness) {
            this.trackExplicitness = trackExplicitness;
        }

        public int getDiscCount() {
            return discCount;
        }

        public void setDiscCount(int discCount) {
            this.discCount = discCount;
        }

        public int getDiscNumber() {
            return discNumber;
        }

        public void setDiscNumber(int discNumber) {
            this.discNumber = discNumber;
        }

        public int getTrackCount() {
            return trackCount;
        }

        public void setTrackCount(int trackCount) {
            this.trackCount = trackCount;
        }

        public int getTrackNumber() {
            return trackNumber;
        }

        public void setTrackNumber(int trackNumber) {
            this.trackNumber = trackNumber;
        }

        public int getTrackTimeMillis() {
            return trackTimeMillis;
        }

        public void setTrackTimeMillis(int trackTimeMillis) {
            this.trackTimeMillis = trackTimeMillis;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPrimaryGenreName() {
            return primaryGenreName;
        }

        public void setPrimaryGenreName(String primaryGenreName) {
            this.primaryGenreName = primaryGenreName;
        }

        public boolean isIsStreamable() {
            return isStreamable;
        }

        public void setIsStreamable(boolean isStreamable) {
            this.isStreamable = isStreamable;
        }
    }
}
