import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveReasonTypeDetailComponent } from 'app/entities/empl-leave-reason-type/empl-leave-reason-type-detail.component';
import { EmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';

describe('Component Tests', () => {
  describe('EmplLeaveReasonType Management Detail Component', () => {
    let comp: EmplLeaveReasonTypeDetailComponent;
    let fixture: ComponentFixture<EmplLeaveReasonTypeDetailComponent>;
    const route = ({ data: of({ emplLeaveReasonType: new EmplLeaveReasonType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveReasonTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplLeaveReasonTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplLeaveReasonTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplLeaveReasonType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplLeaveReasonType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
