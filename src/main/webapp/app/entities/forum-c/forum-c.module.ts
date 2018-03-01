import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JMayaSharedModule } from '../../shared';
import {
    ForumCService,
    ForumCPopupService,
    ForumCComponent,
    ForumCDetailComponent,
    ForumCDialogComponent,
    ForumCPopupComponent,
    ForumCDeletePopupComponent,
    ForumCDeleteDialogComponent,
    forumRoute,
    forumPopupRoute,
} from './';

const ENTITY_STATES = [
    ...forumRoute,
    ...forumPopupRoute,
];

@NgModule({
    imports: [
        JMayaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ForumCComponent,
        ForumCDetailComponent,
        ForumCDialogComponent,
        ForumCDeleteDialogComponent,
        ForumCPopupComponent,
        ForumCDeletePopupComponent,
    ],
    entryComponents: [
        ForumCComponent,
        ForumCDialogComponent,
        ForumCPopupComponent,
        ForumCDeleteDialogComponent,
        ForumCDeletePopupComponent,
    ],
    providers: [
        ForumCService,
        ForumCPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JMayaForumCModule {}
