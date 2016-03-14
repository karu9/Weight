package com.example.pichery.weight.settings;


import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.Profile;
import com.example.pichery.weight.util.DBUtils;

/**
 * Created by pichery on 30/11/15.
 */
public class Settings extends PreferenceFragment {
    DBUtils dbUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        setListeners();
        dbUtil = new DBUtils(getActivity());
    }

    private void setListeners() {
        Preference.OnPreferenceChangeListener spChanged = new  Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (preference.getKey() == getString(R.string.nameKey)) {
                    ((EditTextPreference) preference).setTitle((String) newValue);
                    modifyNameOnDB((String) newValue);
                }
                if (preference.getKey() == getString(R.string.genderKey)) {
                    ((ListPreference) preference).setSummary(getGenderFromId((String) newValue));
                    modifyGenderOnDB(getGenderFromId((String) newValue));
                }
                return true;
            }
        };
        this.findPreference(getString(R.string.nameKey)).setOnPreferenceChangeListener(spChanged);
        this.findPreference(getString(R.string.genderKey)).setOnPreferenceChangeListener(spChanged);
    }



    private void modifyNameOnDB(String name) {
        dbUtil.execute(dbUtil.loadProfile().setName(name));
    }

    private void modifyGenderOnDB(String gender) {
        dbUtil.execute(dbUtil.loadProfile().setSex(gender));
    }

    private String getGenderFromId(String newValue) {
        return getResources().getStringArray(R.array.genderArray)[Integer.valueOf(newValue)-1];
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValuesFromDb();
    }

    private void setValuesFromDb() {
        this.findPreference(getString(R.string.nameKey)).setTitle(dbUtil.loadProfile().getName());
        this.findPreference(getString(R.string.genderKey)).setSummary(dbUtil.loadProfile().getSex());
    }


}
