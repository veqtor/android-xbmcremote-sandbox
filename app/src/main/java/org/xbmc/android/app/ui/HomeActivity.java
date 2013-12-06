package org.xbmc.android.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.DataSync;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.jsonrpc.service.SyncService;
import org.xbmc.android.remotesandbox.R;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import javax.inject.Inject;

/**
 * The landing page of the app.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class HomeActivity extends BaseActivity implements OnRefreshListener {

	@Inject protected EventBus bus;

	private static final String TAG = HomeActivity.class.getSimpleName();
	/**
	 * Sync bridge for global refresh.
	 */
//	private SyncBridge mSyncBridge;
	private PullToRefreshAttacher pullToRefreshAttacher;

	public HomeActivity() {
		super(R.string.title_home, R.layout.activity_home);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Injector.inject(this);
		bus.register(this);

		// only slide menu, not the action bar.
		setSlidingActionBarEnabled(false);

		//pullToRefreshAttacher = PullToRefreshAttacher.get(this);

		// Retrieve the PullToRefreshLayout from the content view
		final PullToRefreshLayout pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

		ActionBarPullToRefresh.from(this)
			// Mark All Children as pullable
			.allChildrenArePullable()
			// Set the OnRefreshListener
			.listener(this)
			// Finally commit the setup to our PullToRefreshLayout
			.setup(pullToRefreshLayout);

	}

	@Override
	public void onDestroy() {
		bus.unregister(this);
		super.onDestroy();
	}

	public void onEvent(DataSync event) {
		if (event.hasFailed()) {
			Toast.makeText(getApplicationContext(), event.getErrorMessage() + " " + event.getErrorHint(), Toast.LENGTH_LONG).show();
		}

		if (!event.hasStarted()) {
			//pullToRefreshAttacher.setRefreshComplete();
		}
	}

	@Override
	public void onRefreshStarted(View view) {

		final long start = System.currentTimeMillis();
		startService(new Intent(Intent.ACTION_SYNC, null, this, SyncService.class)
			.putExtra(SyncService.EXTRA_SYNC_MOVIES, true)
			.putExtra(SyncService.EXTRA_SYNC_MUSIC, true)
		);
		Log.d(TAG, "Triggered refresh in " + (System.currentTimeMillis() - start) + "ms.");
	}

/*	@Override
	protected AbstractSyncBridge[] initSyncBridges() {
		mSyncBridge = new SyncBridge(mRefreshObservers, SyncBridge.SECTION_MUSIC, SyncBridge.SECTION_MOVIES);
		//mSyncBridge = new SyncBridge(mRefreshObservers, SyncBridge.SECTION_MOVIES);
		return new AbstractSyncBridge[]{ mSyncBridge };
	}

	@Override
	protected AbstractSyncBridge getSyncBridge() {
		return mSyncBridge;
	}
*/
}