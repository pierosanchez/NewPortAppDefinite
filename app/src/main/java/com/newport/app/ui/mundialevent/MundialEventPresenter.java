package com.newport.app.ui.mundialevent;

import com.newport.app.data.models.response.MatchsResponse;
import com.newport.app.data.models.response.UserElectionResponse;

import java.util.List;

class MundialEventPresenter implements MundialEventContract.Presenter, MundialEventContract.Callback{

    private MundialEventContract.View mundialMatchesView;

    @Override
    public void getMundialEventMatches() {
        mundialMatchesView.showLoading();
        MundialEventInteractor.getMatches(this);
    }

    @Override
    public void validateUserElection(String dni, String idPartido) {
        mundialMatchesView.showLoading();
        MundialEventInteractor.validateUserElection(dni, idPartido, this);
    }

    @Override
    public void setUserElection(String dni, String election1, String election2, String election3, String election4, String id_partido1, String id_partido2, String id_partido3, String id_partido4) {
        mundialMatchesView.showLoading();
        MundialEventInteractor.setUserElection(dni,  election1,  election2,  election3,  election4,  id_partido1,  id_partido2,  id_partido3,  id_partido4, this);
    }

    @Override
    public void validateUserElectionSuccess(UserElectionResponse userElectionResponse) {
        mundialMatchesView.showValidateUserElectionSucces(userElectionResponse);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void validateUserElectionError(String error) {
        mundialMatchesView.showUserElectionError(error);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void validateUserElectionFaulure(String failure) {
        mundialMatchesView.showUserElectionError(failure);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void getMundialEventSucces(List<MatchsResponse> matchsResponse) {
        mundialMatchesView.showMundialMatchesSuccess(matchsResponse);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void getMundialEventError(String error) {
        mundialMatchesView.showMundialMatchesError(error);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void getMundialEventFailure(String failure) {
        mundialMatchesView.showMundialMatchesError(failure);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void setUserElectionSuccess(UserElectionResponse userElectionResponse) {
        mundialMatchesView.showUserElectionSuccess(userElectionResponse);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void setUserElectionError(String error) {
        mundialMatchesView.showUserElectionError(error);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void setUserElectionFailure(String failure) {
        mundialMatchesView.showUserElectionError(failure);
        mundialMatchesView.hideLoading();
    }

    @Override
    public void attachedView(MundialEventContract.View view) {
        this.mundialMatchesView = view;
    }

    @Override
    public void detachView() {
        mundialMatchesView = null;
    }
}
