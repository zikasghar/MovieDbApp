package com.zik.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularMovie implements Parcelable {
    private String title;
    private String overview;
    private String release;
    private float voteAverage;
    private String posterPath;


    public PopularMovie() {
    }

    public PopularMovie(String title,
                        String overview,
                        String release,
                        float voteAverage,
                        String posterPath) {
        this.title = title;
        this.overview = overview;
        this.release = release;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
    }

    protected PopularMovie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        release = in.readString();
        voteAverage = in.readFloat();
        posterPath = in.readString();
    }

    public static final Creator<PopularMovie> CREATOR = new Creator<PopularMovie>() {
        @Override
        public PopularMovie createFromParcel(Parcel in) {
            return new PopularMovie(in);
        }

        @Override
        public PopularMovie[] newArray(int size) {
            return new PopularMovie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release);
        dest.writeFloat(voteAverage);
        dest.writeString(posterPath);
    }

}
