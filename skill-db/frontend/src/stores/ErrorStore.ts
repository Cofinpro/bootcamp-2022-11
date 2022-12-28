import {defineStore} from "pinia";
import type {AxiosError} from "axios";

export const useErrorStore = defineStore(
    'ErrorStore',
    {state: () =>({
            hasError: Boolean(false),
            errorText: '',
        }),
        actions: {

            catchOverviewError(error: AxiosError) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText ="Unknown error";
                } else {
                    if (error.response.status === 401) {
                        this.errorText = 'Unauthorized!';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren sie Ihren Administrator, falls der Fehler anhält!';
                    } else {
                        this.errorText = 'Unknown Error'
                    }
                }
            },

            catchPostPatchError(error: AxiosError) {
                this.hasError=true;
                if (error.response == undefined) {
                    this.errorText ="Unknown error";
                } else {
                    if (error.response.status === 400) {
                        this.handle400(error);
                    } else if (error.response.status === 401) {
                        this.errorText = 'Unauthorized';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren sie Ihren Administrator, falls der Fehler anhält!';
                    } else if (error.response.status === 404) {
                        this.errorText = `404: ${error.response.data.message}`;
                    } else {
                        this.errorText = 'Unbekannter Fehler!';
                    }
                }
            },

            toggleHasError() {
                this.hasError = false;
                this.errorText= '';
            },

            handle400(error: AxiosError) {
                const message: string = error.response.data.message.toString();
                if (message.includes('Cannot deserialize value of type `java.time.LocalDate`')) {
                    this.errorText= "Unbekanntes Format des Datums!";
                } else if (message === 'Der zurzeit eingelogte Nutzer hat bereits ein Profil!') {
                    this.errorText = message;
                } else if (message.includes('VALIDATION')) {
                    let innerMessage = message.split(/(\[])/)[2];
                    innerMessage = innerMessage.replaceAll(',', ' ');
                    this.errorText = innerMessage;
                } else if (message === 'Datum nicht in passendem Format!') {
                    this.errorText = message;
                } else {
                    this.errorText = "Unbekannter Fehler!";
                }
            },

            catchSkillsJobsPrimariesError(error: AxiosError, name: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText ="Unknown error";
                } else {
                    this.errorText += ` ${name} wurde nicht korrekt vom server erhalten!`
                }
            },

            catchGetError(error: AxiosError, id: number) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = "Unknown error";
                } else {
                    if (error.response.status === 404) {
                        this.errorText = `Profile ${id} existiert nicht!`;
                    } else if (error.response.status === 401) {
                        this.errorText = 'Unauthorized';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren sie Ihren Administrator, falls der Fehler anhält!';
                    } else if (error.response.status === 400) {
                        this.errorText = 'Profile Id konnte nicht aufgelöst werden!'
                    }
                }
            },

            catchDeleteError(error: AxiosError, id: number) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = "Unknown error";
                } else {
                    if (error.response.status === 404) {
                        this.errorText = `Profile ${id} existiert nicht!`;
                    } else if (error.response.status === 401) {
                        this.errorText = 'Recht zum Löschen dieses Profils nicht vorhanden!';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren sie Ihren Administrator, falls der Fehler anhält!';
                    } else if (error.response.status === 400) {
                        this.errorText = 'Profile Id konnte nicht aufgelöst werden!'
                    }
                }
            },
        }
    }
)
