/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JMayaTestModule } from '../../../test.module';
import { ForumCComponent } from '../../../../../../main/webapp/app/entities/forum-c/forum-c.component';
import { ForumCService } from '../../../../../../main/webapp/app/entities/forum-c/forum-c.service';
import { ForumC } from '../../../../../../main/webapp/app/entities/forum-c/forum-c.model';

describe('Component Tests', () => {

    describe('ForumC Management Component', () => {
        let comp: ForumCComponent;
        let fixture: ComponentFixture<ForumCComponent>;
        let service: ForumCService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JMayaTestModule],
                declarations: [ForumCComponent],
                providers: [
                    ForumCService
                ]
            })
            .overrideTemplate(ForumCComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ForumCComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ForumCService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ForumC(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.forums[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
