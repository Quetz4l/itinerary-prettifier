package org.quetz4l;

import org.quetz4l.controller.AppState;
import org.quetz4l.controller.Controller;
import org.quetz4l.service.text.Logger;

public class Prettifier {
    public static void main(String[] args) {
        greetings();
        AppState.saveArgs(args);

        Controller.start();
    }

    private static void greetings() {
        Logger.printMessage(
                """
                                ###########################################################
                                #                                                         #
                                #          Welcome to the Itinerary Prettifier!           #
                                #      Making flight itineraries customer-friendly.       #
                                #                 Made for kood/johvi                     #
                                #                                                         #
                                ###########################################################
                        """, Logger.GREEN);
    }
}