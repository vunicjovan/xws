<template>
    <transition name="fade">
        <div v-if="show" class="md-layout md-gutter md-alignment-center">
            <md-card v-for="ad in getAdvertisements" v-bind:key="ad.id" class="md-layout-item md-xlarge-size-20 md-large-size-25 md-medium-size-40 md-small-size-50 md-xsmall-size-90">
                <md-card-header>
                    <md-card-header-text>
                        <div>
                            <p class="md-title">{{ ad.brand }} {{ ad.model }}</p>
                            <p class="md-subtitle">{{ ad.price }} €</p>
                            <p class="md-subhead">{{ ad.location }}</p>
                        </div>
                    </md-card-header-text>

                    <md-card-media class="md-layout-item md-size-55">
                        <img src="../../assets/audi.jpg" alt="Vehicle image">
                        <!-- <img :src="ad.photo[0]" alt="Vehicle image"> -->
                    </md-card-media>
                </md-card-header>

                <md-card-actions>
                    <md-button @click.native="$router.push('/single-ad/' + ad.id)">View advertisement</md-button>
                </md-card-actions>
            </md-card>
        </div>
    </transition>
</template>

<script>
import { mapGetters, mapAction, mapActions } from "vuex";
export default {
    name: "StandardView",
    data: function() {
        return {
            show: false
        };
    },
    mounted: function() {
        this.$store.dispatch("getAllAdvertisements");
        this.show = !this.show;
    },
    computed: {
		...mapGetters(["getAdvertisements"])
    },
    methods: {
        ...mapActions(["getAllAdvertisements"]),
    }
}
</script>

<style>
    .md-card {
        margin: 2.5%;
        display: inline-block;
        vertical-align: top;
    }

    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 2.5s;
    }

    .fade-enter,
    .fade-leave-to {
        opacity: 0;
    }
</style>
