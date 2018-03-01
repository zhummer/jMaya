import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JMayaSharedModule } from '../../shared';
import {
    HostCService,
    HostCPopupService,
    HostCComponent,
    HostCDetailComponent,
    HostCDialogComponent,
    HostCPopupComponent,
    HostCDeletePopupComponent,
    HostCDeleteDialogComponent,
    hostRoute,
    hostPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hostRoute,
    ...hostPopupRoute,
];

@NgModule({
    imports: [
        JMayaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HostCComponent,
        HostCDetailComponent,
        HostCDialogComponent,
        HostCDeleteDialogComponent,
        HostCPopupComponent,
        HostCDeletePopupComponent,
    ],
    entryComponents: [
        HostCComponent,
        HostCDialogComponent,
        HostCPopupComponent,
        HostCDeleteDialogComponent,
        HostCDeletePopupComponent,
    ],
    providers: [
        HostCService,
        HostCPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JMayaHostCModule {}
