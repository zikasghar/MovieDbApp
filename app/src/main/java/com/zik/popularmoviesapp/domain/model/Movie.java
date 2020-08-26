package com.zik.popularmoviesapp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Popular Movie Object
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class Movie implements Parcelable {
    private String id;
    private String title;
    private String overview;
    @SerializedName("release_date")
    private String release;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private float voteAverage;
    private float popularity;


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(String id,
                 String title,
                 String overview,
                 String release,
                 String posterPath,
                 float voteAverage,
                 float popularity) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release = release;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        release = in.readString();
        posterPath = in.readString();
        voteAverage = in.readFloat();
        popularity = in.readFloat();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release);
        dest.writeString(posterPath);
        dest.writeFloat(voteAverage);
        dest.writeFloat(popularity);
    }

}
