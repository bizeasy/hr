import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortPurposeDetailComponent } from 'app/entities/work-effort-purpose/work-effort-purpose-detail.component';
import { WorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';

describe('Component Tests', () => {
  describe('WorkEffortPurpose Management Detail Component', () => {
    let comp: WorkEffortPurposeDetailComponent;
    let fixture: ComponentFixture<WorkEffortPurposeDetailComponent>;
    const route = ({ data: of({ workEffortPurpose: new WorkEffortPurpose(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortPurposeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortPurposeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortPurposeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortPurpose on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortPurpose).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
