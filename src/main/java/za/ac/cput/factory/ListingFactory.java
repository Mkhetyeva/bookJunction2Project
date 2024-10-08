package za.ac.cput.factory;

import za.ac.cput.domain.Book;
import za.ac.cput.domain.Listing;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class ListingFactory {
    public static Listing createListing(int listingID, Book book, User userID, LocalDate dateListed, Double price, String status, String condition) {
        if (Helper.isNullOrEmpty(String.valueOf(listingID)) || Helper.isNullOrEmpty(String.valueOf(book)) || Helper.isNullOrEmpty(String.valueOf(userID)) || Helper.isNullOrEmpty(String.valueOf(dateListed))
                || Helper.isNullOrEmpty(String.valueOf(price)) || Helper.isNullOrEmpty(status) || Helper.isNullOrEmpty(condition))
            return null;

        return new Listing.Builder().setListingID(listingID)
                .setBookID(book)
                .setUserID(userID)
                .setDateListed(dateListed)
                .setPrice(price)
                .setStatus(status)
                .setCondition(condition)
                .build();
    }
}
