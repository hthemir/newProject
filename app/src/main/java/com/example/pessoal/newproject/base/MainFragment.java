package com.example.pessoal.newproject.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.pessoal.newproject.R;

/**
 * Created by ZUP on 25/09/2017.
 */

public class MainFragment extends Fragment implements MainMVP.RequiredViewOperations {
    protected final String TAG = getClass().getSimpleName();

    //mantem estado dos objetos inscritos durante mudancas de configuracao
    private final StateMaintainer mStateMaintainer = new StateMaintainer(this.getActivity().getFragmentManager(), TAG);

    //referencia ao presenter para realizar operacoes
    private MainMVP.PresenterOperations mPresenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startMVPOperations();
    }

    //inicia e reinicia o presenter
    //precisa ser chamado apos onCreate de Activity
    public void startMVPOperations() {
        try {
            if (mStateMaintainer.firstTimeIn()) {
                Log.d(TAG, "first time calling onCreate()");
                initialize(this);
            } else {
                Log.d(TAG, "not first time calling onCreate()");
                reinitialize(this);
            }
        } catch (Exception e) {
            Log.d(TAG, "onCreate() error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //inicializa os objetos relevantes para o MVP
    //cria uma instancia do presenter
    //salva o presenter no StateMaintainer
    // informa aa instancia do presenter que o objeto foi criado
    private void initialize(MainMVP.RequiredViewOperations view) throws java.lang.InstantiationException, IllegalAccessException {
        mPresenter = new MainPresenter(view);
        mStateMaintainer.put(MainMVP.PresenterOperations.class.getSimpleName(), mPresenter);
    }

    //recupera o presenter
    //informa aa instancia do presenter que houve uma mudanca de configuracao na view
    //se o presenter foi perdido, um novo eh instanciado
    private void reinitialize(MainMVP.RequiredViewOperations view) throws java.lang.InstantiationException, IllegalAccessException {
        mPresenter = mStateMaintainer.get(MainMVP.PresenterOperations.class.getSimpleName());
        if (mPresenter == null) {
            Log.w(TAG, "creating the presenter again for it was lost");
            initialize(view);
        } else {
            mPresenter.onConfigurationChanged(view);
        }
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlert(String message) {
        //show alert box
    }
}
