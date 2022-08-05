import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveDetailComponent } from 'app/entities/empl-leave/empl-leave-detail.component';
import { EmplLeave } from 'app/shared/model/empl-leave.model';

describe('Component Tests', () => {
  describe('EmplLeave Management Detail Component', () => {
    let comp: EmplLeaveDetailComponent;
    let fixture: ComponentFixture<EmplLeaveDetailComponent>;
    const route = ({ data: of({ emplLeave: new EmplLeave(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplLeaveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplLeaveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplLeave on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplLeave).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
