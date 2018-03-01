import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ForumCComponent } from './forum-c.component';
import { ForumCDetailComponent } from './forum-c-detail.component';
import { ForumCPopupComponent } from './forum-c-dialog.component';
import { ForumCDeletePopupComponent } from './forum-c-delete-dialog.component';

export const forumRoute: Routes = [
    {
        path: 'forum-c',
        component: ForumCComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.forum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'forum-c/:id',
        component: ForumCDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.forum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const forumPopupRoute: Routes = [
    {
        path: 'forum-c-new',
        component: ForumCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.forum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'forum-c/:id/edit',
        component: ForumCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.forum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'forum-c/:id/delete',
        component: ForumCDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.forum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
