package com.example.epuser.pickcontacts.FAQpackage;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by epuser on 6/16/2017.
 */

public class Questions implements Parcelable {

    private String query;
    private String solution;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }



    public Questions(Parcel in) {
        query = in.readString();
        solution=in.readString();
    }





    public Questions(String query,String solution) {
        this.query = query;
        this.solution=solution;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(query);
        dest.writeString(solution);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
}

