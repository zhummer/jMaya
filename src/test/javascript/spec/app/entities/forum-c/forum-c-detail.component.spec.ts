/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JMayaTestModule } from '../../../test.module';
import { ForumCDetailComponent } from '../../../../../../main/webapp/app/entities/forum-c/forum-c-detail.component';
import { ForumCService } from '../../../../../../main/webapp/app/entities/forum-c/forum-c.service';
import { ForumC } from '../../../../../../main/webapp/app/entities/forum-c/forum-c.model';

describe('Component Tests', () => {

    describe('ForumC Management Detail Component', () => {
        let comp: ForumCDetailComponent;
        let fixture: ComponentFixture<ForumCDetailComponent>;
        let service: ForumCService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JMayaTestModule],
                declarations: [ForumCDetailComponent],
                providers: [
                    ForumCService
                ]
            })
            .overrideTemplate(ForumCDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ForumCDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ForumCService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ForumC(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.forum).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
