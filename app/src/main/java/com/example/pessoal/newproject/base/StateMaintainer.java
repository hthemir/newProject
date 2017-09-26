package com.example.pessoal.newproject.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by ZUP on 25/09/2017.
 */

public class StateMaintainer {
    protected final String TAG = getClass().getSimpleName();

    private final WeakReference<FragmentManager> mFragmentManager;
    private final String mStateMaintainerTag;
    private StateMngFragment mStateMaintainerFrag;

    //fragmentManager repassa uma referencia do FragmentManager
    //stateMaintainerTag eh a TAG utilizada para inserir o fragmento responsavel por manter os objetos "vivos"
    public StateMaintainer(FragmentManager fragmentManager, String stateMaintainerTag) {
        mFragmentManager = new WeakReference<FragmentManager>(fragmentManager);
        mStateMaintainerTag = stateMaintainerTag;
    }

    //cria (se true) ou recupera (se false) o fragmento responsavel por armazenar objetos
    public boolean firstTimeIn() {
        try {
            //recuperar referencia
            mStateMaintainerFrag = (StateMngFragment) mFragmentManager.get().findFragmentByTag(mStateMaintainerTag);
            //criar novo RetainedFragment
            if (mStateMaintainerFrag == null) {
                Log.d(TAG, "Creating new RetainedFragment " + mStateMaintainerTag);
                mStateMaintainerFrag = new StateMngFragment();
                mFragmentManager.get().beginTransaction().add(mStateMaintainerFrag, mStateMaintainerTag).commit();
                return true;
                //"recuperar" RetainedFragment existente
            } else {
                Log.d(TAG, "Returning existent RetainedFragment " + mStateMaintainerTag);
                return false;
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "error firstTimeIn(): " + e.getMessage());
            return false;
        }
    }

    //insere objeto a ser preservado durante mudancas de configuracao
    //delega para o StateMgnFragment a insercao de um objeto (obj) a ser mantido e sua tag de referencia(key)
    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key,obj);
    }

    //insere objeto a ser preservado durante mudancas de configuracao
    //delega para o StateMgnFragment a insercao de um objeto (obj) a ser mantido
    //utiliza a classe do objeto como tag de referencia
    //deve ser usado somente uma vez por classe, caso contrario pode gerar conflitos na recuperacao de objeto
    public void put(Object obj) {
        mStateMaintainerFrag.put(obj);
    }

    //recupera objeto salvo
    //key eh a tag de referencia e T um tipo generico de retorno
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return mStateMaintainerFrag.get(key);
    }

    //verifica a existencia de um objeto utilizando a tag de referencia (key)
    //true se existe, false caso contrario
    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }

    //armazena e administra os objetos que devem ser preservados durante mudancas de configuracao
    //instanciado somente uma vez
    //utiliza HashMap para salvar os objetos e suas chaves de referencia
    public static class StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //garante que o fragmento sera preservado durante mudancas de configuracao
            setRetainInstance(true);
        }

        //insere objetos no hashmap
        //key eh a chave de referencia, obj eh o objeto a ser salvo
        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        //insere objeto no hashmap utilizando o nome da classe como referencia
        public void put(Object obj) {
            put(obj.getClass().getName(), obj);
        }

        //recupera objeto no hashmap
        //T eh a classe do objeto
        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }
}

