/*
 * Copyright (c) 2017. Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jahirfiquitiva.libs.frames.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import jahirfiquitiva.libs.frames.R;
import jahirfiquitiva.libs.frames.fragments.CollectionFragment;
import jahirfiquitiva.libs.frames.utils.ColorUtils;
import jahirfiquitiva.libs.frames.utils.FavoritesUtils;
import jahirfiquitiva.libs.frames.utils.ThemeUtils;
import jahirfiquitiva.libs.frames.utils.ToolbarTinter;

public class FavoritesActivity extends AppCompatActivity {

    private CollectionFragment favsFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);

        FavoritesUtils.init(this);

        setContentView(R.layout.activity_favorites);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favsFragment = CollectionFragment.newInstance(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, favsFragment, "favs")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        ToolbarTinter.on(menu, toolbar)
                .setIconsColor(ColorUtils.getMaterialPrimaryTextColor(!(ColorUtils.isLightColor
                        (ThemeUtils.darkOrLight(this, R.color.dark_theme_primary, R.color
                                .light_theme_primary)))))
                .forceIcons()
                .reapplyOnChange(true)
                .apply(this);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FavoritesUtils.destroy(this);
    }

    @Override
    public void onBackPressed() {
        finishAndSendData();
    }

    private void finishAndSendData() {
        Intent intent = new Intent();
        intent.putExtra("hasModified", favsFragment != null && favsFragment.hasModifiedFavs());
        setResult(11, intent);
        finish();
    }

}