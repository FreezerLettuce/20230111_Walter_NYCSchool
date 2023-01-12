package com.example.nyschools.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nyschools.di.Network;
import com.example.nyschools.model.NYCSchool;
import com.example.nyschools.model.SatScores;
import com.example.nyschools.model.UIState;
import com.example.nyschools.service.Repository;
import com.example.nyschools.service.RepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private Repository repository;

    private MutableLiveData<UIState> _schools = new MutableLiveData(new UIState.Loading());
    public LiveData<UIState> schools = _schools;

    private MutableLiveData<UIState> _scores = new MutableLiveData(new UIState.Loading());
    public LiveData<UIState> scores = _scores;

    // the one we store when click item in the recycler view
    public NYCSchool selectedSchool;
    public List<SatScores> scoresFetched = new ArrayList<>();

    public MainViewModel() {
        repository = new RepositoryImpl(Network.INSTANCE.getRetrofitService());

        getSchools();
        getScores();
    }

    public void getSelectedSATScore() {
        if (selectedSchool != null && !scoresFetched.isEmpty()) {
            for(int i=0; i < scoresFetched.size(); i++) {
                if(selectedSchool.getDbn().equals(scoresFetched.get(i).getDbn())) {
                    _scores.postValue(new UIState.Success(scoresFetched.get(i)));
                }
            }
        }  else {
            _scores.postValue(new UIState.Error("No school selected"));
        }
    }

    private void getSchools() {
        repository.fetchSchools()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SingleObserver<List<NYCSchool>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull List<NYCSchool> nycSchools) {
                                _schools.postValue(new UIState.Success(nycSchools));
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                _schools.postValue(new UIState.Error(e.getLocalizedMessage()));
                            }
                        }
                );
    }

    private void getScores() {
        repository.fetchSatScores()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SingleObserver<List<SatScores>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull List<SatScores> satScores) {
                                scoresFetched.clear();
                                scoresFetched.addAll(satScores);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                _scores.postValue(new UIState.Error(e.getLocalizedMessage()));
                            }
                        }
                );
    }

    public void retryGetSchools() {
        getSchools();
    }
}
