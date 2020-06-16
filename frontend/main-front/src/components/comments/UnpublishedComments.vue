<template>
    <div class="somediv">
        <div class="md-headline">Comments to be published</div>
        <md-list class="md-triple-line md-dense" v-for="comm in getUnpublishedCommentList" v-bind:key="comm.id">
            <md-list-item>
                <md-avatar>
                    <i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
                </md-avatar>

                <div class="md-list-item-text">
                    <span class="ownerc">{{ comm.owner }}</span>
                    <span class="contentc">{{ comm.title }}</span>
                    <p>{{ comm.content }}</p>
                </div>
                
                <span class="some-span">
                    <md-button @click="acceptComment(comm)" class="md-icon-button md-list-action">
                        <i class="fas fa-check-circle fa-2x"></i>
                        <md-tooltip>Publish comment</md-tooltip>
                    </md-button>
                    <md-button @click="declineComment(comm)" class="md-icon-button md-list-action">
                        <i class="fas fa-times-circle fa-2x"></i>
                        <md-tooltip>Reject comment</md-tooltip>
                    </md-button>
                </span>
            </md-list-item>
            <md-divider class="md-inset"></md-divider>
        </md-list>
    </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
    name: "UnpublishedComments",
    computed: {
        ...mapGetters(["getUnpublishedCommentList"]),
    },
    mounted: function() {
        this.$store.dispatch("getUnpublishedComments");
    },
    methods: {
        ...mapActions(["getUnpublishedComments", "publishComment", "rejectComment"]),
        acceptComment(comm) {
            this.$store.dispatch("publishComment", comm);
        },
        declineComment(comm) {
            this.$store.dispatch("rejectComment", comm);
        }
    }
}
</script>

<style>
    p {
        word-break: break-all;
        white-space: normal;
        padding-right: 30%;
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    }
    .ownerc {
        font-family: 'Courier New', Courier, monospace;
    }
    .contentc {
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    }
    .some-span {
        padding-right: 2.5%;
    }
    .somediv {
        width: 70%;
        margin: 0 auto;
    }
    .md-headline {
        width: 50%;
        margin: 2.5% auto;
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
        text-align: center;
        vertical-align: middle;
    }
</style>
