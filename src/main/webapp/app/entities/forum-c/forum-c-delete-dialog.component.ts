import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ForumC } from './forum-c.model';
import { ForumCPopupService } from './forum-c-popup.service';
import { ForumCService } from './forum-c.service';

@Component({
    selector: 'jhi-forum-c-delete-dialog',
    templateUrl: './forum-c-delete-dialog.component.html'
})
export class ForumCDeleteDialogComponent {

    forum: ForumC;

    constructor(
        private forumService: ForumCService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.forumService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'forumListModification',
                content: 'Deleted an forum'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-forum-c-delete-popup',
    template: ''
})
export class ForumCDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private forumPopupService: ForumCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.forumPopupService
                .open(ForumCDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
