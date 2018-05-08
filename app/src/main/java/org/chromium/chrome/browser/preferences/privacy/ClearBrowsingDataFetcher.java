// Copyright 2018 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.chrome.browser.preferences.privacy;

import org.chromium.base.metrics.RecordHistogram;
import org.chromium.chrome.browser.ChromeFeatureList;

import java.util.Arrays;

/**
 * Requests information about important sites and other forms of browsing data.
 */
public class ClearBrowsingDataFetcher
        implements BrowsingDataBridge.ImportantSitesCallback,
                   BrowsingDataBridge.OtherFormsOfBrowsingHistoryListener {
    // This is a constant on the C++ side.
    private int mMaxImportantSites;
    // This is the sorted list of important registerable domains. If null, then we haven't finished
    // fetching them yet.
    private String[] mSortedImportantDomains;
    // These are the reasons the above domains were chosen as important.
    private int[] mSortedImportantDomainReasons;
    // These are full url examples of the domains above. We use them for favicons.
    private String[] mSortedExampleOrigins;

    // Whether the dialog about other forms of browsing history should be shown.
    private boolean mIsDialogAboutOtherFormsOfBrowsingHistoryEnabled;

    ClearBrowsingDataFetcher() {
        mMaxImportantSites = BrowsingDataBridge.getMaxImportantSites();
    }

    /**
     * Fetch important sites if the feature is enabled.
     */
    public void fetchImportantSites() {
        if (ChromeFeatureList.isEnabled(ChromeFeatureList.IMPORTANT_SITES_IN_CBD)) {
            BrowsingDataBridge.fetchImportantSites(this);
        }
    }

    /**
     * Request information about other forms of browsing history if the history dialog hasn't been
     * shown yet.
     */
    public void requestInfoAboutOtherFormsOfBrowsingHistory() {
        if (!OtherFormsOfHistoryDialogFragment.wasDialogShown())
            BrowsingDataBridge.getInstance().requestInfoAboutOtherFormsOfBrowsingHistory(this);
    }

    /**
     * @return The maximum number of important sites to show.
     */
    public int getMaxImportantSites() {
        return mMaxImportantSites;
    }

    /**
     * @return Get a sorted list of important registerable domains. If null, then we haven't
     * finished fetching them yet.
     */
    public String[] getSortedImportantDomains() {
        return mSortedImportantDomains;
    }

    /**
     * @return The reasons the above domains were chosen as important.
     */
    public int[] getSortedImportantDomainReasons() {
        return mSortedImportantDomainReasons;
    }

    /**
     * @return Full url examples of the domains above. We use them for favicons.
     */
    public String[] getSortedExampleOrigins() {
        return mSortedExampleOrigins;
    }

    /**
     * @return Whether the dialog about other forms of browsing history should be shown.
     */
    public boolean isDialogAboutOtherFormsOfBrowsingHistoryEnabled() {
        return mIsDialogAboutOtherFormsOfBrowsingHistoryEnabled;
    }

    @Override
    public void onImportantRegisterableDomainsReady(String[] domains, String[] exampleOrigins,
            int[] importantReasons, boolean dialogDisabled) {
        if (domains == null || dialogDisabled) return;
        // mMaxImportantSites is a constant on the C++ side. While 0 is valid, use 1 as the minimum
        // because histogram code assumes a min >= 1; the underflow bucket will record the 0s.
        RecordHistogram.recordLinearCountHistogram("History.ClearBrowsingData.NumImportant",
                domains.length, 1, mMaxImportantSites + 1, mMaxImportantSites + 1);
        mSortedImportantDomains = Arrays.copyOf(domains, domains.length);
        mSortedImportantDomainReasons = Arrays.copyOf(importantReasons, importantReasons.length);
        mSortedExampleOrigins = Arrays.copyOf(exampleOrigins, exampleOrigins.length);
    }

    @Override
    public void enableDialogAboutOtherFormsOfBrowsingHistory() {
        mIsDialogAboutOtherFormsOfBrowsingHistoryEnabled = true;
    }
}