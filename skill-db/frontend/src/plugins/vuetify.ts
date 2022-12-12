import {createVuetify} from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

export default createVuetify({
    components,
    directives,
    theme: {
        themes: {
            light: {
                colors: {
                    primary: "#EF7D00",
                    secondary: "#CDDCF3",
                    tertiary: "#847575",
                }
            },
        },
    },
})
