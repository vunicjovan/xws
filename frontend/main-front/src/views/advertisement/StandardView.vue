<template>
    <transition name="fade">
        <div v-if="show" class="md-layout md-gutter md-alignment-center">
            <md-card class="md-layout-item md-xlarge-size-20 md-large-size-25 md-medium-size-40 md-small-size-50 md-xsmall-size-90" v-for="ad in ads" v-bind:key="ad.id">
                <md-card-header>
                    <md-card-header-text>
                        <div>
                            <p class="md-title">{{ ad.brand }} {{ ad.model }}</p>
                            <p class="md-subtitle">{{ ad.price }} EU</p>
                            <p class="md-subhead">{{ ad.place }}</p>
                        </div>
                    </md-card-header-text>

                    <md-card-media class="md-layout-item md-size-55">
                        <img src="../../assets/audi.jpg" alt="Vehicle image">
                    </md-card-media>
                </md-card-header>

                <md-card-actions>
                    <md-button @click.native="$router.push('/single-ad')">View advertisement</md-button>
                </md-card-actions>
            </md-card>
        </div>
    </transition>
</template>

<script>
export default {
    name: "StandardView",
    data: function() {
        return {
            show: false,
            ads: [
                {
                    "id": 1,
                    "price": 100,
                    "place": "Beograd",
                    "brand": "BMW",
                    "model": "X8",
                    "photo": []
                },
                {
                    "id": 2,
                    "price": 2000,
                    "place": "Novi Sad",
                    "brand": "Audi",
                    "model": "R8",
                    "photo": []
                },
                {
                    "id": 3,
                    "price": 1008,
                    "place": "Kragujevac",
                    "brand": "BMW",
                    "model": "X8",
                    "photos": []
                },
                {
                    "id": 4,
                    "price": 100,
                    "place": "Beograd",
                    "brand": "BMW",
                    "model": "X8",
                    "photo": []
                },
                {
                    "id": 5,
                    "price": 100,
                    "place": "Beograd",
                    "brand": "BMW",
                    "model": "X8",
                    "photo": []
                },
                {
                    "id": 6,
                    "price": 100,
                    "place": "Beograd",
                    "brand": "BMW",
                    "model": "X8",
                    "photo": []
                }
            ]
        };
    },
    mounted: function() {
        this.show = !this.show;
    }
}
</script>

<style>
    /* .custom-card {
        min-width: 300px;
        max-width: 500px;
        min-height: 200px;
        max-height: 250px;
        display: block;
    } */

    .md-card {
        margin: 2.5%;
        display: inline-block;
        vertical-align: top;
    }

    .nana {
        align-items: center;
		justify-content: center;
		padding: 5%;
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
