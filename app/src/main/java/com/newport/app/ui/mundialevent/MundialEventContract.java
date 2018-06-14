package com.newport.app.ui.mundialevent;

import com.newport.app.data.models.response.MatchsResponse;
import com.newport.app.data.models.response.UserElectionResponse;
import com.newport.app.util.BasePresenter;

import java.util.List;

public interface MundialEventContract {
    interface View {

        void showLoading();

        void hideLoading();

        void showValidateUserElectionSucces(UserElectionResponse matchsResponse);
        void showValidateUserElectionErorr(String error);

        void showMundialMatchesSuccess(List<MatchsResponse> matchsResponse);
        void showMundialMatchesError(String error);

        void showUserElectionSuccess(UserElectionResponse userElectionResponse);
        void showUserElectionError(String error);
    }

    interface Presenter extends BasePresenter<View> {

        void getMundialEventMatches();
        void validateUserElection(String dni, String idPartido);
        void setUserElection(String dni, String election1, String election2, String election3, String election4,
                             String id_partido1, String id_partido2, String id_partido3, String id_partido4);

    }

    interface Callback {

        void validateUserElectionSuccess(UserElectionResponse userElectionResponse);
        void validateUserElectionError(String error);
        void validateUserElectionFaulure(String failure);

        void getMundialEventSucces(List<MatchsResponse> matchsResponse);
        void getMundialEventError(String error);
        void getMundialEventFailure(String failure);

        void setUserElectionSuccess(UserElectionResponse userElectionResponse);
        void setUserElectionError(String error);
        void setUserElectionFailure(String failure);

    }
}
