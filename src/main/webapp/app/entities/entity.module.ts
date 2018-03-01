import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JMayaForumCModule } from './forum-c/forum-c.module';
import { JMayaHostCModule } from './host-c/host-c.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JMayaForumCModule,
        JMayaHostCModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JMayaEntityModule {}
