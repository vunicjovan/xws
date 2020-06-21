<template>
  <div class="myDiv" v-if="isLogged && getUser !== null && getUser.roles.includes('AGENT')">
        <div class="md-headline">Statistic report for agent {{ getUser.firstName }} {{ getUser.lastName }}</div>
        <md-list :md-expand-single="true">
            <md-list-item md-expand>
                <span class="md-list-item-text">
                    Best rated advertisements
                </span>

                <md-list slot="md-expand">
                    <md-list-item v-for="ad in getStatistic.bestRatedAds" v-bind:key="ad.advertisementId" class="md-inset">
                        <span class="md-list-item-text">
                            <md-button @click.native="$router.push('/single-ad/' + ad.advertisementId)" :md-ripple="false">
                                <b>{{ getAdIndex(getStatistic.bestRatedAds, ad) }}.</b> <i>Rating {{ ad.rating }}</i>
                            </md-button>
                        </span>
                    </md-list-item>
                </md-list>
            </md-list-item>

            <md-list-item md-expand>
                <span class="md-list-item-text">Most commented advertisements</span>

                <md-list slot="md-expand">
                    <md-list-item v-for="ad in getStatistic.mostCommentedAds" v-bind:key="ad.advertisementId" class="md-inset">
                        <span class="md-list-item-text">
                            <md-button @click.native="$router.push('/single-ad/' + ad.advertisementId)" :md-ripple="false">
                                <b>{{ getAdIndex(getStatistic.mostCommentedAds, ad) }}.</b> <i>In total: {{ ad.commentNumber }} comments</i>
                            </md-button>
                        </span>
                    </md-list-item>
                </md-list>
            </md-list-item>

            <md-list-item md-expand>
                <span class="md-list-item-text">Vehicles with longest distance traveled</span>

                <md-list slot="md-expand">
                    <md-list-item v-for="ad in getStatistic.mostKmTraveledAds" v-bind:key="ad.advertisementId" class="md-inset">
                        <span class="md-list-item-text">
                            <md-button @click.native="$router.push('/single-ad/' + ad.advertisementId)" :md-ripple="false">
                                <b>{{ getAdIndex(getStatistic.mostKmTraveledAds, ad) }}.</b> <i>In total: {{ ad.kmTraveled }} kilometers traveled</i>
                            </md-button>
                        </span>
                    </md-list-item>
                </md-list>
            </md-list-item>

        </md-list>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
    name: "StatisticReport",
    computed: {
		...mapGetters(["isLogged", "getUser", "getStatistic"]),
    },
    mounted: function() {
        this.$store.dispatch("getStatisticReport", this.$route.params.id);
    },
    methods: {
        ...mapActions(["getStatisticReport"]),

        getAdIndex(list, ad) {
            return list.indexOf(ad) + 1;
        },
    }
}
</script>

<style>
    .myDiv .md-list {
        width: 80%;
        margin: 0 auto;
    }
</style>
