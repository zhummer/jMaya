import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ForumC } from './forum-c.model';
import { ForumCService } from './forum-c.service';

@Component({
    selector: 'jhi-forum-c-detail',
    templateUrl: './forum-c-detail.component.html'
})
export class ForumCDetailComponent implements OnInit, OnDestroy {

    forum: ForumC;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private forumService: ForumCService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInForums();
    }

    load(id) {
        this.forumService.find(id)
            .subscribe((forumResponse: HttpResponse<ForumC>) => {
                this.forum = forumResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInForums() {
        this.eventSubscriber = this.eventManager.subscribe(
            'forumListModification',
            (response) => this.load(this.forum.id)
        );
    }
}
