package com.daniel.tvlauncher

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.VerticalGridPresenter


class MainFragment : VerticalGridSupportFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "TV Launcher"
        gridPresenter = VerticalGridPresenter().apply { numberOfColumns = 5 }

        adapter = ArrayObjectAdapter(CardPresenter()).apply {
            setItems(loadApps(), null)
            setOnItemViewClickedListener { _, item, _, _ ->
                (item as? AppCard)?.let { launchApp(it) }
            }
        }
    }

    private fun loadApps(): List<AppCard> {
        val pm = requireActivity().packageManager
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        return pm.queryIntentActivities(intent, 0)
            .map {
                AppCard(
                    it.loadLabel(pm).toString(),
                    it.loadIcon(pm),
                    it.activityInfo.packageName
                )
            }
            .sortedBy { it.title }
    }

    private fun launchApp(appCard: AppCard) {
        try {
            requireActivity().packageManager
                .getLaunchIntentForPackage(appCard.packageName)
                ?.let { startActivity(it) }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error launching app", Toast.LENGTH_SHORT).show()
        }
    }
}