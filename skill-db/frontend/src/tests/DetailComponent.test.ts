import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import vuetify from "@/plugins/vuetify";
import DetailComponent from "@/components/DetailComponent.vue";
import {createTestingPinia} from "@pinia/testing";
import {useUserStore} from "@/stores/UserStore";
import {useDetailStore} from "@/stores/DetailStore";
import {ConvertToUserModel} from "@/models/UserModel";

vi.mock('vue-router', async () => ({
    ...await vi.importActual('vue-router'),
    useRoute: vitest.fn(() => ({
        params: {
            id: 1
        }
    })),
}));

describe('DetailComponent',() => {
    it('not locked user is locked', async () => {
        const wrapper = mount(DetailComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const userStore = useUserStore();
        const detailStore = useDetailStore();

        userStore.loadUserById = vitest.fn(async () => {
            userStore.user = ConvertToUserModel.toUserModel({
                locked: false
            })
        });
        const spyLoadDetails = vitest.spyOn(detailStore, 'loadDetailsById');
        const spyLoadUser = vitest.spyOn(userStore, 'loadUserById');
        const spyLock = vitest.spyOn(userStore, 'lockUser');

        await wrapper.vm.lockFunction.method();

        expect(spyLoadDetails).toBeCalledTimes(1);
        expect(spyLoadUser).toBeCalledTimes(1);
        expect(spyLock).toBeCalledTimes(1);
        expect(spyLock).toBeCalledWith('');
    });

    it('locked user is still locked', async () => {
        const wrapper = mount(DetailComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const userStore = useUserStore();
        const detailStore = useDetailStore();

        userStore.loadUserById = vitest.fn(async () => {
            userStore.user = ConvertToUserModel.toUserModel({
                locked: true
            })
        });
        const spyLoadDetails = vitest.spyOn(detailStore, 'loadDetailsById');
        const spyLoadUser = vitest.spyOn(userStore, 'loadUserById');
        const spyLock = vitest.spyOn(userStore, 'lockUser');

        await wrapper.vm.lockFunction.method();

        expect(spyLoadDetails).toBeCalledTimes(1);
        expect(spyLoadUser).toBeCalledTimes(1);
        expect(spyLock).not.toBeCalled();
    });
});