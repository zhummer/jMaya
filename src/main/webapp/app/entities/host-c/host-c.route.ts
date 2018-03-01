import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HostCComponent } from './host-c.component';
import { HostCDetailComponent } from './host-c-detail.component';
import { HostCPopupComponent } from './host-c-dialog.component';
import { HostCDeletePopupComponent } from './host-c-delete-dialog.component';

export const hostRoute: Routes = [
    {
        path: 'host-c',
        component: HostCComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.host.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'host-c/:id',
        component: HostCDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.host.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hostPopupRoute: Routes = [
    {
        path: 'host-c-new',
        component: HostCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.host.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'host-c/:id/edit',
        component: HostCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.host.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'host-c/:id/delete',
        component: HostCDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jMayaApp.host.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
