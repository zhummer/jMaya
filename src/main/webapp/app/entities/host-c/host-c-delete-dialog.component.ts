import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HostC } from './host-c.model';
import { HostCPopupService } from './host-c-popup.service';
import { HostCService } from './host-c.service';

@Component({
    selector: 'jhi-host-c-delete-dialog',
    templateUrl: './host-c-delete-dialog.component.html'
})
export class HostCDeleteDialogComponent {

    host: HostC;

    constructor(
        private hostService: HostCService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hostService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hostListModification',
                content: 'Deleted an host'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-host-c-delete-popup',
    template: ''
})
export class HostCDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hostPopupService: HostCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hostPopupService
                .open(HostCDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
