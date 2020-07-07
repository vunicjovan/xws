<template>
    <div class="myDiv">
        <div class="md-headline">Statistic report</div>
        <div id="stats" v-if="getStatistic">
            <br/>
			<div v-if="getStatistic.bestRatedAds && getStatistic.bestRatedAds.length != 0">
				<span md-heading>Best rated ads</span>
				<md-table>
					<md-table-row>
						<md-table-cell class="cell">Advertisement</md-table-cell>
						<md-table-cell class="cell">Rating</md-table-cell>
					</md-table-row>
					<md-table-row v-for="ad1 in getStatistic.bestRatedAds" v-bind:key="ad1.id">
						<md-table-cell class="cell"><b>{{ getAdIndex(getStatistic.bestRatedAds, ad1) }}. {{ ad1.advertisementName }}</b></md-table-cell>
                        <md-table-cell class="cell">{{ ad1.rating }}</md-table-cell>
					</md-table-row>
				</md-table>
			</div>
            <br/>
			<div v-if="getStatistic.mostCommentedAds && getStatistic.mostCommentedAds.length != 0">
				<span md-heading>Most commented ads</span>
				<md-table>
					<md-table-row>
                        <md-table-cell class="cell">Advertisement</md-table-cell>
						<md-table-cell class="cell">Comment count</md-table-cell>
					</md-table-row>
					<md-table-row v-for="ad2 in getStatistic.mostCommentedAds" v-bind:key="ad2.id">
						<md-table-cell class="cell"><b>{{ getAdIndex(getStatistic.mostCommentedAds, ad2) }}. {{ ad2.advertisementName }}</b></md-table-cell>
                        <md-table-cell class="cell">{{ ad2.commentNumber }}</md-table-cell>
                    </md-table-row>
				</md-table>
			</div>
            <br/>
			<div v-if="getStatistic.mostKmTraveledAds && getStatistic.mostKmTraveledAds.length != 0">
				<span md-heading>Most kilometers traveled ads</span>
				<md-table>
					<md-table-row>
						<md-table-cell class="cell">Advertisement</md-table-cell>
						<md-table-cell class="cell">Kilometers traveled</md-table-cell>
					</md-table-row>
					<md-table-row v-for="ad3 in getStatistic.mostKmTraveledAds" v-bind:key="ad3.id">
						<md-table-cell class="cell"><b>{{ getAdIndex(getStatistic.mostKmTraveledAds, ad3) }}. {{ ad3.advertisementName }}</b></md-table-cell>
                        <md-table-cell class="cell">{{ ad3.kmTraveled }}</md-table-cell>
                    </md-table-row>
				</md-table>
			</div>
		</div>
    </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
    name: "StatisticReport",
    computed: {
		...mapGetters(["getStatistic"]),
    },
    mounted: function() {
        this.$store.dispatch("getAdvertisementStatistic", 1);
    },
    methods: {
        ...mapActions(["getAdvertisementStatistic"]),

        getAdIndex(list, ad) {
            return list.indexOf(ad) + 1;
        },
    }
}
</script>

<style>
    #stats {
        width: 60%;
        margin: 0 auto;
    }
    .cell {
        width: 50%;
    }
</style>
