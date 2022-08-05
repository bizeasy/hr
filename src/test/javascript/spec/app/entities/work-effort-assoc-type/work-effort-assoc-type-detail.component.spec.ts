import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortAssocTypeDetailComponent } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type-detail.component';
import { WorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';

describe('Component Tests', () => {
  describe('WorkEffortAssocType Management Detail Component', () => {
    let comp: WorkEffortAssocTypeDetailComponent;
    let fixture: ComponentFixture<WorkEffortAssocTypeDetailComponent>;
    const route = ({ data: of({ workEffortAssocType: new WorkEffortAssocType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortAssocTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortAssocTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortAssocTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortAssocType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortAssocType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
